package com.benoitthore.keyframe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.keyframe.examples.SeekBarExampleFragment
import kotlinx.android.synthetic.main.selector_fragment.*

class SelectorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.selector_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        example_list.layoutManager = LinearLayoutManager(context!!)
        example_list.adapter = SelectorAdapter(

            listOf(
                "Seekbar" to { SeekBarExampleFragment() }
            )
        ) {
            (activity as MainActivity).addFragment(it())

        }
    }
}

class SelectorAdapter(val list: List<Pair<String, () -> Fragment>>, val onClick: (() -> Fragment) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(TextView(parent.context)) {}
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val textView = (holder.itemView as TextView)

        val (name, fragmentBuilder) = list[position]
        textView.text = name
        textView.setOnClickListener {
            onClick(fragmentBuilder)
        }
    }

}
