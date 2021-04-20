package com.glc.sideslipdeleteandtop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvSample:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvSample=findViewById(R.id.rv_sample)

        //垂直布局管理器
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        rvSample.layoutManager=layoutManager

        val list=ArrayList<String>()

        for (i in 0..10){
            list.add("我是第${i}个")
        }

        val sampleAdapter=SampleAdapter(list)
        rvSample.adapter=sampleAdapter

        //置顶
        sampleAdapter.onItemClickTop={v,pos->
            sampleAdapter.top(pos)
        }
        //删除
        sampleAdapter.onItemClickDelete={v,pos->
            sampleAdapter.delete(pos)
        }

    }
}