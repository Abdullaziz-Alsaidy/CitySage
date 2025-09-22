package com.aou.citysage.data

import android.util.Log
import com.aou.citysage.data.models.Place
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.uni.sehhaty.Utilitie.Appointment
import com.uni.sehhaty.Utilitie.Doctor
import com.uni.sehhaty.Utilitie.Hospital
import com.uni.sehhaty.Utilitie.UserProfile
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = Firebase.firestore

    // Get current user ID (from Firebase Authentication)
    private val currentUserId: String?
        get() = auth.currentUser?.uid



    // New funcs


    suspend fun getPlaces(): List<Place> {
        return try {
            val snapshot = db.collection("Places").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Place::class.java)?.copy(id = doc.id)
            }.also {
                Log.d("FirebaseRepository", "Fetched ${it.size} places: $it")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching places: ${e.message}", e)
            emptyList()
        }
    }
    suspend fun getPlace(placeID: String): Place? {
        return try {
            val snapshot = db.collection("Places")
                .document(placeID)
                .get()
                .await()
            if (snapshot.exists()) {
                val place = snapshot.toObject(Place::class.java)?.copy(id = snapshot.id)
                Log.d("FirebaseRepository", "Fetched place with ID $placeID: $place")
                place
            } else {
                Log.w("FirebaseRepository", "Place with ID $placeID does not exist")
                null
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching place with ID $placeID: ${e.message}", e)
            null
        }
    }

    // ================= Hospitals =================
    suspend fun getAllHospitals(): List<Hospital> {
        return try {
            val snapshot = db.collection("Hospitals")
                .get()
                .await()
            val hospitals = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Hospital::class.java)?.copy(id = doc.id)
            }
            Log.d("FirebaseRepository", "Fetched ${hospitals.size} hospitals: $hospitals")
            hospitals
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching hospitals: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getHospitalById(hospitalId: String): Hospital? {
        return try {
            db.collection("Hospitals")
                .document(hospitalId)
                .get()
                .await()
                .toObject(Hospital::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // ================= Doctors =================
    suspend fun getAllDoctors(): List<Doctor> {
        return try {
            db.collection("Doctors" +
                    "")
                .get()
                .await()
                .toObjects(Doctor::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getDoctorsByHospital(hospitalId: String): List<Doctor> {
        return try {
            db.collection("Doctors")
                .whereEqualTo("hospitalId", hospitalId)
                .get()
                .await()
                .toObjects(Doctor::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getDoctorById(doctorId: String): Doctor? {
        return try {
            db.collection("Doctors")
                .document(doctorId)
                .get()
                .await()
                .toObject(Doctor::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // ================= Appointments =================
    suspend fun createAppointment(
        place: Place
    )  {

// Add a new document with a generated ID
        db.collection("Places")
            .add(place)
            .addOnSuccessListener { documentReference ->
                Log.d("DocumentSnapshot","DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("DocumentSnapshot","error ${e.message}")
            }
    }

    suspend fun getUserAppointments():
            List<Appointment> {
        val userId = currentUserId ?: return emptyList()

        return try {
            Log.d("T!@#","User ID :${userId}")
            db.collection("Appointments")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(Appointment::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAppointmentsByHospital(hospitalId: String): List<Appointment> {
        val userId = currentUserId ?: return emptyList()

        return try {
            db.collection("Appointments")
                .whereEqualTo("userId", userId)
                .whereEqualTo("hospitalId", hospitalId)
                .get()
                .await()
                .toObjects(Appointment::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

//    suspend fun updateAppointmentStatus(appointmentId: String, newStatus: String): Boolean {
//        return try {
//            db.collection("Appointments")
//                .document(appointmentId)
//                .update("status", newStatus)
//                .await()
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }

//    suspend fun cancelAppointment(appointmentId: String): Boolean {
//        return updateAppointmentStatus(appointmentId, "Cancelled")
//    }

    // ================= User Profile =================
    suspend fun saveUserProfile(
        firstName: String,
        lastName: String,
        phone: String
    ): Boolean {
        val userId = currentUserId ?: return false
        val email = auth.currentUser?.email ?: ""

        val userProfile = UserProfile(
            uid = userId,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = email
        )

        return try {
            db.collection("Users")
                .document(userId)
                .set(userProfile)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserProfile(): UserProfile? {
        val userId = currentUserId ?: return null
        Log.d("G!@#","currentID :${userId}")

        return try {
            db.collection("Users")
                .document(userId)
                .get()
                .await()
                .toObject(
                    UserProfile::class.java
                )
        } catch
            (e: Exception) {
            null
        }
    }

    // ================= Search =================
    suspend fun searchDoctors(): List<Doctor> {
        return try {
            val snapshot = db.collection("Doctors")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Doctor::class.java)?.copy(id = doc.id)
            }.also { Log.d("FirebaseRepository", "Doctors retrieved: ${it.size}") }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching doctors: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun searchHospitals(): List<Hospital> {
        return try {
            val snapshot = db.collection("Hospitals")
                .orderBy("name")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Hospital::class.java)?.copy(id = doc.id)
            }.also { Log.d("FirebaseRepository", "Hospitals retrieved: ${it.size}") }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching hospitals: ${e.message}", e)
            emptyList()
        }
    }
}