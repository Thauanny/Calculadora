package com.example.projeto01_calculadora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private var editText: TextInputEditText? = null
    private var fragmentCalculadura = CalculadoraFragment();
    private var fragmentNotas =  NotasFragment();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout = findViewById<TabLayout>(R.id.tabLayout);
        viewPager2 = findViewById<ViewPager2>(R.id.viewPager2);
        tabLayout.addTab(tabLayout.newTab().setText("Calculadora"))
        tabLayout.addTab(tabLayout.newTab().setText("Notas"))

        val fragmentList = listOf(
            fragmentCalculadura,
            fragmentNotas
        )

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragmentList.size
            override fun createFragment(position: Int): Fragment = fragmentList[position]
        }


        viewPager2.adapter = adapter


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })



    }
}