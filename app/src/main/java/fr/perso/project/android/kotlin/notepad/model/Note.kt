package fr.perso.project.android.kotlin.notepad.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created on 29/04/2020 - 22:20.
 * Note class
 *
 * @author : JEAN-LOUIS Thessalène
 */
data class Note (var title: String ="",
                 var content : String = "",
                 var filename : String = "") : Parcelable, Serializable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        //Identifiant unique qui va permettre à la JVM de reconnaître les versions de notre classe Note
        //Non obligatoire - Peut être généré par défaut mais la version du compilateur peut influer donc vaut mieux le préciser
        private val serialVersionUid : Long = 424242

        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}