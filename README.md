# SideslipDeleteAndTop
RecyclerView置顶删除Demo
实现的效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210420163551339.gif#pic_center)

引入第三方依赖,详细的使用教程请访问github官方文档
[SwipeDelMenuLayout](https://github.com/mcxtzhang/SwipeDelMenuLayout/blob/master/README-cn.md)
build.gradle(Project)

```bash
 maven { url "https://jitpack.io" }
```
build.gradle(module)

```bash
 implementation 'com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0'
```
SampleAdapter  RecyclerView适配器

```bash
class SampleAdapter(val list: ArrayList<String>): RecyclerView.Adapter<SampleAdapter.ViewHolder>() {
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvName:TextView=view.findViewById(R.id.tvName)
        val btnTop:TextView=view.findViewById(R.id.btnTop)
        val btnDelete:TextView=view.findViewById(R.id.btnDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_sample,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: SampleAdapter.ViewHolder, position: Int) {
        holder.tvName.setText(list.get(position))

        //删除按钮
        holder.btnDelete.setOnClickListener {
            onItemClickDelete.invoke(it,position)
        }
        //置顶按钮
        holder.btnTop.setOnClickListener {
            onItemClickTop.invoke(it,position)
        }
    }

    //删除
    fun delete(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }
    //置顶
    fun top(position: Int){
        list.add(0,list.removeAt(position))
        notifyDataSetChanged()
    }

    lateinit var onItemClickDelete: (v: View, pos: Int) -> Unit//删除
    lateinit var onItemClickTop: (v: View, pos: Int) -> Unit//置顶
}
```
item_sample.xml条目布局
这里就使用到我们刚刚引入的依赖

```bash
<?xml version="1.0" encoding="utf-8"?>
<com.mcxtzhang.swipemenulib.SwipeMenuLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:clickable="true"
    android:orientation="horizontal">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="59dp">
            <TextView
                android:id="@+id/tvName"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="center"
                android:text="202020"/>
        </LinearLayout>
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#d0d0d0"/>
    </LinearLayout>
    <Button
        android:id="@+id/btnTop"
        android:layout_width="60dp"
        android:layout_height="match_parent"
        android:background="#BEBEBE"
        android:text="置顶"
        android:textColor="@android:color/white"/>
    <Button
        android:id="@+id/btnDelete"
        android:layout_width="60dp"
        android:layout_height="match_parent"
        android:background="#ff0000"
        android:text="删除"
        android:textColor="@android:color/white"/>

</com.mcxtzhang.swipemenulib.SwipeMenuLayout>
```
activity_main.xml

```bash
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_sample"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```
MianActivity.xml

```bash
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
```
