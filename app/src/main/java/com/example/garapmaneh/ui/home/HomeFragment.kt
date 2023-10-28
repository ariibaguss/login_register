package com.example.garapmaneh.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.garapmaneh.R
import com.example.garapmaneh.UserAdapter
import com.example.garapmaneh.databinding.FragmentHomeBinding
import com.example.garapmaneh.model.DataItem
import com.example.garapmaneh.model.ResponseUser
import com.example.garapmaneh.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.rv_users)
        adapter = UserAdapter(mutableListOf())

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        setupSearchView()
        getUser()

        return root
    }

    private fun setupSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredData = filterDataByName(newText)
                adapter.setData(filteredData)
                return true
            }
        })
    }

    private fun filterDataByName(query: String?): List<DataItem> {
        val dataArray = adapter.getData()
        if (query.isNullOrBlank()) {
            return dataArray
        } else {
            return dataArray.filter { user ->
                (user.firstName + " " + user.lastName).contains(query, ignoreCase = true)
            }
        }
    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("2")

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data ?: emptyList()
                    adapter.setData(dataArray)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
