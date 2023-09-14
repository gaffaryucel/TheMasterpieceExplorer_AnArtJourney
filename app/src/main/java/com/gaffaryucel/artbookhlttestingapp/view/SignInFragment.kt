package com.gaffaryucel.artbookhlttestingapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gaffaryucel.artbookhlttestingapp.MainActivity2
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentSignInBinding
import com.gaffaryucel.artbookhlttestingapp.util.Status
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding : FragmentSignInBinding
    private var loaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentSignInBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        setUpVideoView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cirLoginButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.signInWithEmailAndPassword(email, password)
        }
        binding.goToSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragment2ToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
            when(isSuccess.status){
                Status.SUCCESS->{
                    binding.goToSignUp.visibility  = View.INVISIBLE
                    binding.signInErrorText.visibility = View.INVISIBLE
                    val intent = Intent(requireActivity(),MainActivity2::class.java)
                    requireActivity().finish()
                    startActivity(intent)
                }
                Status.LOADING->{
                    binding.goToSignUp.visibility  = View.VISIBLE
                    binding.signInErrorText.visibility = View.INVISIBLE
                }
                Status.ERROR->{
                    binding.goToSignUp.visibility  = View.INVISIBLE
                    binding.signInErrorText.visibility = View.VISIBLE
                    binding.signInErrorText.text = isSuccess.message
                    Toast.makeText(requireContext(),  isSuccess.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun setUpVideoView(){
        if (!loaded){
            binding.videoLayout.visibility = View.VISIBLE
            binding.signInLayout.visibility = View.INVISIBLE
            val videoPath = "android.resource://com.gaffaryucel.artbookhlttestingapp/${R.raw.splash_screan_video}"
            val videoUri = Uri.parse(videoPath)
            binding.videoView.setVideoURI(videoUri)
            binding.videoView.setOnPreparedListener { mediaPlayer ->
                // Videonun hazır olduğunda oynatma başlatılacak
                mediaPlayer.start()
            }
            binding.videoView.setOnCompletionListener {
                if (viewModel.getCurrentUser() != null){
                    // Videonun oynatılması tamamlandığında MainActivity2'ye geçiş yapın
                    val intent = Intent(requireActivity(),MainActivity2::class.java)
                    requireActivity().finish()
                    startActivity(intent)
                }else{
                    binding.videoLayout.visibility = View.GONE
                    binding.signInLayout.visibility = View.VISIBLE
                }
            }
            loaded = true
        }

    }

}