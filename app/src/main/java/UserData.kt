import android.net.Uri
import java.util.Calendar

class UserData(
    fullName:String,
    userName:String,
    eMail:String,
    dateOfBirth:Calendar,
    gender:String,
    mobileNumber:String,
    aboutUser:String,
    profilePic:Uri?,
){
    lateinit var fullName: String
    lateinit var userName: String
    lateinit var eMail: String
    lateinit var dateOfBirth: Calendar
    lateinit var gender: String
    lateinit var mobileNumber: String
    lateinit var aboutUser: String
    var profilePic: Uri?

    init {
        this.fullName=fullName
        this.userName=userName
        this.eMail=eMail
        this.dateOfBirth=dateOfBirth
        this.gender=gender
        this.mobileNumber=mobileNumber
        this.aboutUser=aboutUser
        this.profilePic=profilePic
    }
    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "Full Name" to fullName,
            "User Name" to userName,
            "E-mail" to eMail,
            "DOB" to dateOfBirth,
            "Gender" to gender,
            "Mobile" to mobileNumber,
            "About" to aboutUser,
            "Profile Pic" to profilePic
        )
    }
}