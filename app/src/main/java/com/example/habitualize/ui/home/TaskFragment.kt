package com.example.habitualize.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.habitualize.R
import com.example.habitualize.databinding.FragmentTaskBinding
import com.example.habitualize.ui.home.Adapter.TaskAdapter
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.home.viewmodels.TaskViewModel
import com.example.habitualize.ui.models.BlogModel
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.ui.premium.PremiumActivity
import com.example.habitualize.utils.*
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels()

    lateinit var taskAdapter: TaskAdapter
    lateinit var binding: FragmentTaskBinding
    var button: CardView? = null
    private var ivCancelIcon: ImageView? = null
    private var tv_blog_title: TextView? = null
    private var cv_save_btn: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        val currentHour = Calendar.getInstance(TimeZone.getDefault())
        binding.tvGreetings.text = when (currentHour.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                resources.getString(R.string.greeting1)
            }
            in 12..16 -> {
                resources.getString(R.string.greeting2)
            }
            else -> {
                resources.getString(R.string.greeting3)
            }
        }

        initAdapter()
        initListeners()
        initObservers()
        viewModel.getCommentData(SharePrefHelper.readString(languageCode).toString())
        viewModel.getChallengesFromDb(
            requireActivity(),
            SharePrefHelper.readString(languageCode).toString()
        )

        return binding.root
    }

    private fun initListeners() {
        binding.taskDrawerOpenBtn.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }
        binding.premiumButton.setOnClickListener {
            startActivity(Intent(requireContext(), PremiumActivity::class.java))
        }
    }

    private fun initAdapter() {
        taskAdapter =
            TaskAdapter(requireContext(), arrayListOf(), object : TaskAdapter.TaskItemListener {
                override fun onItemClicked(
                    position: Int,
                    dailyChallengesModel: DailyChallengesModel
                ) {
                    if (dailyChallengesModel.isOpened) {
                        var intent = Intent(requireContext(), TaskDetailActivity::class.java)
                        intent.putExtra("challenge_name", dailyChallengesModel.challenge_name)
                        intent.putExtra("isHidden", false)
                        startActivity(intent)
                    } else {
                        showConfirmationDialog(dailyChallengesModel)
                    }
                }
            }, SharePrefHelper.readInteger(selectedColorIndex))
        binding.taskRv.adapter = taskAdapter
    }

    private fun initObservers() {
        viewModel.challengesList.observe(requireActivity()) {
            taskAdapter.challengesList.clear()
            taskAdapter.challengesList.addAll(it)
            taskAdapter.notifyDataSetChanged()
        }

        viewModel.daily_quote.observe(requireActivity()) {
            binding.tvQuote.setLetterDuration(90); // sets letter duration programmatically
            binding.tvQuote.text =
                it // sets the text with animation (Read "KNOWN BUGS" if it doesn't give desired results)
            binding.tvQuote.isAnimating // returns current boolean animation state (optional)
        }

        viewModel.blog_model.observe(requireActivity()) {
            HomeActivity.blogLink = it.link
            if (SharePrefHelper.readString(isBlogDialogShowed) != it.isShow) {
                if ((activity as HomeActivity).rate_us_dialog != null && (activity as HomeActivity).rate_us_dialog!!.isShowing) {
                    (activity as HomeActivity).rate_us_dialog?.dismiss()
                }
                SharePrefHelper.writeString(isBlogDialogShowed, it.isShow)
                showBlogLinkDialog(it)

            }
        }

        viewModel.relaxing_music_url.observe(requireActivity()) {
            HomeActivity.musicLink = it
        }

    }

    private fun showBlogLinkDialog(data: BlogModel) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_blogs_link)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ivCancelIcon = dialog.findViewById<ImageView>(R.id.iv_cancel_icon)
        val iv_thumbnail = dialog.findViewById<ImageView>(R.id.iv_thumbnail)
        tv_blog_title = dialog.findViewById<TextView>(R.id.tv_blog_title)
        cv_save_btn = dialog.findViewById<CardView>(R.id.cv_save_btn)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        dialog.show()


        Handler(Looper.myLooper()!!).postDelayed({
            Glide.with(requireContext()).load(data.image_link).placeholder(R.drawable.loader_image)
                .into(iv_thumbnail)
        },1000)


        tv_blog_title?.text = data.title
        ivCancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

        cv_save_btn?.setOnClickListener {
            try {
                val uri = Uri.parse(data.link)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = uri
                startActivity(i)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invite_link_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun showConfirmationDialog(dailyChallengesModel: DailyChallengesModel) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_confirmation_dialouge)
        button = dialog.findViewById(R.id.confirmation_btn)
        var tv_challenge_emoji = dialog.findViewById<TextView>(R.id.tv_challenge_emoji)
        var icon = dialog.findViewById<ImageView>(R.id.dialoge_img)
        var tv_challenge_name = dialog.findViewById<TextView>(R.id.tv_challenge_name)
        dialog.show()

        tv_challenge_name.text = dailyChallengesModel.challenge_name
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        Glide.with(requireContext())
            .load(Uri.parse(dailyChallengesModel.challenge_emoji))
            .into(icon)

        if (dailyChallengesModel.isUserLocal) {
            tv_challenge_emoji.visibility = View.VISIBLE
            icon.visibility = View.GONE
            tv_challenge_emoji.text = dailyChallengesModel.challenge_emoji
        } else {
            tv_challenge_emoji.visibility = View.GONE
            icon.visibility = View.VISIBLE
        }
        button?.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(requireContext(), TaskDetailActivity::class.java)
            intent.putExtra("challenge_name", dailyChallengesModel.challenge_name)
            startActivity(intent)

            //counter
            var _counter = SharePrefHelper.readInteger(openTaskCounter) + 1
            SharePrefHelper.writeInteger(openTaskCounter, _counter)
        }
    }

    private fun updateTheme(position: Int) {
        when (position) {
            0 -> {
                setThemeColor(R.color.theme)
            }
            1 -> {
                setThemeColor(R.color.theme_2)
            }
            2 -> {
                setThemeColor(R.color.theme_3)
            }
            3 -> {
                setThemeColor(R.color.theme_4)
            }
            4 -> {
                setThemeColor(R.color.theme_5)
            }
            5 -> {
                setThemeColor(R.color.theme_6)
            }
            6 -> {
                setThemeColor(R.color.theme_7)
            }
            7 -> {
                setThemeColor(R.color.theme_8)
            }
        }
    }

    private fun setThemeColor(color: Int) {
        changeStatusBarColor(requireActivity(), color)
        binding.premiumButton.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
        binding.taskParent.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        button.let { it?.setCardBackgroundColor(ContextCompat.getColor(requireContext(), color)) }
        cv_save_btn.let {
            it?.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    color
                )
            )
        }
        ivCancelIcon.let { it?.setColorFilter(ContextCompat.getColor(requireContext(), color)) }
    }
}