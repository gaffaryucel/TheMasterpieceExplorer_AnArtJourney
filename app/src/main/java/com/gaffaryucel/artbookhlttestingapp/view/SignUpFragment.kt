package com.gaffaryucel.artbookhlttestingapp.view

import android.content.Intent
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
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentSignUpBinding
import com.gaffaryucel.artbookhlttestingapp.util.Status
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        // Giriş butonuna tıklama olayını dinleyerek signInWithEmailAndPassword fonksiyonunu çağırabilirsiniz.
        binding.signUpButton.setOnClickListener {
            val name = binding.nameEdittext.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.registerWithEmailAndPassword(name,email, password)
        }
        binding.goToSignInFragment.setOnClickListener{
            findNavController().popBackStack()
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.registrationSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                val intent = Intent(requireActivity(),MainActivity2::class.java)
                requireActivity().finish()
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "İşlem başarısız \n daha sonra tekrar deneyiniz", Toast.LENGTH_SHORT).show()
                // Giriş başarısız, hata mesajını kullanıcıya gösterebiliriz.
            }
        })
        viewModel.registrationChecker.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    binding.signUpProgressBar.visibility  = View.INVISIBLE
                    binding.signUpErrorText.visibility = View.INVISIBLE
                }
                Status.LOADING->{
                    binding.signUpProgressBar.visibility  = View.VISIBLE
                    binding.signUpErrorText.visibility = View.INVISIBLE
                }
                Status.ERROR->{
                    binding.signUpProgressBar.visibility  = View.INVISIBLE
                    binding.signUpErrorText.visibility = View.VISIBLE
                    binding.signUpErrorText.text = it.message
                }
            }
        })
    }

}