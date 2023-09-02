package com.gaffaryucel.artbookhlttestingapp.view

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
import com.gaffaryucel.artbookhlttestingapp.MainActivity2
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentSignInBinding
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        val videoPath = "android.resource://com.gaffaryucel.artbookhlttestingapp/${R.raw.splash_iki}"
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


        // Giriş butonuna tıklama olayını dinleyerek signInWithEmailAndPassword fonksiyonunu çağırabilirsiniz.
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
            if (isSuccess) {
                /*
                val intent = Intent(requireActivity(),MainActivity2::class.java)
                requireActivity().finish()
                startActivity(intent)
                 */
            } else {
                Toast.makeText(requireContext(), "İşlem başarısız \n daha sonra tekrar deneyiniz", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
/*
           val intent = Intent(requireContext(),MainActivity2::class.java)
            requireActivity().finish()
            startActivity(intent)
 */