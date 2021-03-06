package com.takusemba.jethub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.takusemba.jethub.R
import com.takusemba.jethub.databinding.ItemFeedBinding
import com.takusemba.jethub.model.Language
import com.takusemba.jethub.ui.fragment.FeedFragment
import com.takusemba.jethub.ui.item.FeedRepoSection
import com.takusemba.jethub.viewmodel.FeedViewModel
import com.takusemba.jethub.viewmodel.UserViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class FeedAdapter(
  private val userViewModel: UserViewModel,
  private val feedViewModel: FeedViewModel,
  private val fragment: FeedFragment
) : PagerAdapter() {

  private val languages = Language.POPULAR_LANGUAGES

  override fun instantiateItem(group: ViewGroup, position: Int): Any {
    val inflater = LayoutInflater.from(group.context)
    val binding = DataBindingUtil
      .inflate<ItemFeedBinding>(inflater, R.layout.item_feed, group, false)

    val context = binding.root.context
    val language = languages[position]
    val feedRepoSection = FeedRepoSection(language, fragment, feedViewModel, userViewModel)

    val linearLayoutManager = LinearLayoutManager(context)
    val groupAdapter = GroupAdapter<ViewHolder>().apply {
      add(feedRepoSection)
    }
    binding.recyclerView.layoutManager = linearLayoutManager
    binding.recyclerView.adapter = groupAdapter

    group.addView(binding.root)
    return binding.root
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return languages[position].title
  }

  override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
    collection.removeView(view as View)
  }

  override fun getCount(): Int {
    return languages.size
  }

  override fun isViewFromObject(view: View, anything: Any): Boolean {
    return view === anything
  }
}