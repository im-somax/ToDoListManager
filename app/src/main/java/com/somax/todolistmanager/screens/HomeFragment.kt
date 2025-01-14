package com.somax.todolistmanager.screens

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.somax.todolistmanager.R
import com.somax.todolistmanager.database.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //code for hiding the keyboard
        val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

         launch{
             context?.let {
                 val notes = NoteDatabase(it).getNoteDao().getAllNotes()

                 if(notes.count() > 0){
                     tvEmpty.visibility = View.GONE
                     recycler_view.visibility = View.VISIBLE
                     recycler_view.adapter = NotesAdapter(notes)
                 } else {
                     tvEmpty.visibility = View.VISIBLE
                     recycler_view.visibility = View.GONE
                 }
             }
         }


        button_add.setOnClickListener{
            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
