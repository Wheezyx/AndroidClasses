package pl.wedel.lab4.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_class_race_add.*
import pl.wedel.lab4.Class
import pl.wedel.lab4.MainActivity
import pl.wedel.lab4.R
import pl.wedel.lab4.Race
import pl.wedel.lab4.database.DBHelper

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClassRaceAddFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var DBHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        DBHelper = DBHelper(context as Context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_class_race_add, container, false)
        val pickPhotoBtn = view.findViewById<Button>(R.id.pickPhotoBtn)
        val saveBtn = view.findViewById<Button>(R.id.addButton)
        saveBtn.setOnClickListener { saveEntity() }
        pickPhotoBtn.setOnClickListener { pickPhoto() }
        return view
    }

    private fun saveEntity() {

        val bitmap = image.drawable as BitmapDrawable

        val saveType = this.arguments?.getString("SAVE")
        var message = ""
        if (saveType.equals("RACE")) {
            val entity = Race(0, name.text.toString(), description.text.toString(), bitmap.bitmap)
            DBHelper.insertRace(entity)
            message = "${entity.name} added"

        } else if (saveType.equals("CLASS")) {
            val entity = Class(0, name.text.toString(), description.text.toString(), bitmap.bitmap)
            DBHelper.insertClass(entity)
            message = "${entity.name} added"
        }
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("SAVE_MESSAGE", message)
        startActivity(intent)
    }

    private val PICK_PHOTO_FOR_AVATAR = 200

    private fun pickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null)
                return
            val inputStream = context!!.contentResolver.openInputStream(data.data)
            image.setImageBitmap(BitmapFactory.decodeStream(inputStream))
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassRaceAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
