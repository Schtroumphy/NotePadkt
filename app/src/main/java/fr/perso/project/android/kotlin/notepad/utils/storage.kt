package fr.perso.project.android.kotlin.notepad.utils

import android.content.Context
import android.text.TextUtils
import fr.perso.project.android.kotlin.notepad.model.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

/**
 * Created on 30/04/2020 - 00:00.
 * TODO: Add a class header comment!
 *
 * @author : JEAN-LOUIS Thessalène
 */


private val TAG = "storage"

fun persistNote(context : Context, note : Note){

    if(TextUtils.isEmpty(note.filename)){
        //Génére des noms de fichiers uniques
        note.filename = UUID.randomUUID().toString() + "note"
    }
    //Ouverture d'un fichier en écriture
    //Context de l'appli nous donne accès à des fichiers stockés d  ns le dossier
    //unique de l'appli
    val fileOutput = context.openFileOutput(note.filename, Context.MODE_PRIVATE)

    //Flux de données qui va écrire dans le fichier qu'on a ouvert
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

//Charge une note à partir d'un fichier
private fun loadNote(context : Context, filename : String) : Note{
    //Ouvrir un fichier en mode lecture
    val fileInput = context.openFileInput(filename)

    //Flux de données qui va écrire dans le fichier qu'on a ouvert
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}

//Charge une note à partir d'un fichier
fun loadNotes(context : Context) : MutableList<Note>{
    val notes = mutableListOf<Note>()

    //Dossier de notre appli
    val notesDir = context.filesDir

    for(filename in notesDir.list()){
        val note = loadNote(context, filename)
        println("Loaded note : ${note}")
        notes.add(note)
    }
    return notes
}

fun deleteNoteInFile(context : Context, note : Note){
    context.deleteFile(note.filename)
}