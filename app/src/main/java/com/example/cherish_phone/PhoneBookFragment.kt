package com.example.cherish_phone

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.view.*

import kotlinx.android.synthetic.main.fragment_phone_book.view.*


class PhoneBookFragment : Fragment() {

    lateinit var madapter: PhoneAdapter
    var phonelist= mutableListOf<Phone>()
    var searchText=""
    var sortText="asc"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_phone_book, container, false)
        setContentView(view)
        return view

    }

    fun setContentView(view:View){
        phonelist.addAll(getPhoneNumbers(sortText,searchText))
        madapter=PhoneAdapter(phonelist)
        view.recycler.adapter=madapter
        view.recycler.layoutManager=LinearLayoutManager(context)
    }

    fun getPhoneNumbers(sort:String,name:String):List<Phone>{
        val list= mutableListOf<Phone>()

        val phonUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER)
        // 2.2 조건 정의
        var where:String? = null
        var whereValues:Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
       if(name.isNotEmpty()){
           where= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"= ?"
           whereValues= arrayOf(name)
       }
        val optionSort =ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" $sort"

        context?.run {
            val cursor=contentResolver.query(phonUri,projections,where,whereValues,optionSort)

            while (cursor?.moveToNext()==true){
                val id= cursor?.getString(0)
                val name=cursor?.getString(1)
                val number=cursor?.getString(2)

                val phone=Phone(id,name,number)

                list.add(phone)
            }
        }
        // 결과목록 반환

        return list
    }


}