package fr.perso.project.android.kotlin.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Created on 29/04/2020 - 23:34.
 * TODO: Add a class header comment!
 *
 * @author : JEAN-LOUIS Thessalène
 */
class ConfirDeleteNoteDialogFragment(val noteTitle : String) : DialogFragment() {

    interface ConfirmDeleteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener : ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Etes-vous sûr de vouloir supprimer cette note \"${noteTitle}\"?")
            .setPositiveButton("Supprimer", DialogInterface.OnClickListener { dialog, id ->
                listener?.onDialogPositiveClick()
            })
            .setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, id ->
                listener?.onDialogNegativeClick()
            })

        return builder.create()
    }
}