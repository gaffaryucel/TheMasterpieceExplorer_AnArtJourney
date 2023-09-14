package com.gaffaryucel.artbookhlttestingapp.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gaffaryucel.artbookhlttestingapp.MainActivity
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentArtListBinding
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentEditProfileBinding
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.EditProfileViewModel

class EditProfileFragment : Fragment() {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: FragmentEditProfileBinding
    private var currentUserModel = UserModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signOutButton.setOnClickListener{
            viewModel.signOut()
            val intent = Intent(requireActivity(),MainActivity::class.java)
            requireActivity().finish()
            requireActivity().startActivity(intent)
        }
        binding.saveChangesButton.setOnClickListener {
            val newName = binding.userNameEditText.text.toString()
            val newMAil = binding.emailEditText.text.toString()
            val newModel = UserModel(newName,newMAil,"")
            viewModel.updateDataInFirebase(currentUserModel,newModel)
        }
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            currentUserModel = it
            binding.apply {
                user = it
            }
            if (!it.profilePhoto.isNullOrEmpty()){
                Glide.with(requireContext()).load(it.profilePhoto).into(binding.profileImageView)
            }
        })
    }

}