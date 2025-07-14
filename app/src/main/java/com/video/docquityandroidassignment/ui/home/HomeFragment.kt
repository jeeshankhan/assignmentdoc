package com.video.docquityandroidassignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.video.docquityandroidassignment.api.ApiController
import com.video.docquityandroidassignment.R
import com.video.docquityandroidassignment.base.BaseAdapterBinding
import com.video.docquityandroidassignment.base.BaseAdapterBindingTwo
import com.video.docquityandroidassignment.databinding.FragmentHomeBinding
import com.video.docquityandroidassignment.databinding.ItemProgressBinding
import com.video.docquityandroidassignment.databinding.ItemTaskGroupBinding
import com.video.docquityandroidassignment.model.DashboardData
import com.video.docquityandroidassignment.model.InProgressSection
import com.video.docquityandroidassignment.model.InProgressTask
import com.video.docquityandroidassignment.model.TaskGroup
import com.video.docquityandroidassignment.model.TaskGroupsSection
import com.video.docquityandroidassignment.model.TodayTaskSummary
import com.video.docquityandroidassignment.model.User
import com.video.docquityandroidassignment.ui.home.HomeFragment.MockDashboard.dashboardResponse
import com.video.docquityandroidassignment.ui.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),BaseAdapterBinding.BindAdapterListener,BaseAdapterBindingTwo.BindAdapterListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseAdapter:BaseAdapterBinding<InProgressTask>
    private lateinit var baseAdapterTwo:BaseAdapterBindingTwo<TaskGroup>
    private val viewModel: HomeViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycleViewTask()
        setRecycleViewProgress() // dataDash.inProgress.tasks
        baseAdapter.setData(dashboardResponse.inProgress.tasks)
        baseAdapterTwo.setData(dashboardResponse.taskGroups.groups)
        binding.setDataInUI()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentHomeBinding.setDataInUI(){
        with(includeTask){
            tvMessage.text = dashboardResponse.todayTaskSummary.title
            tvProgressPercent.text = "${ dashboardResponse.todayTaskSummary.progressPercentage}%"
            progressIndicator.progress = dashboardResponse.todayTaskSummary.progressPercentage
        }

        tvTitleTaskCount.text = dashboardResponse.inProgress.total.toString()
        tvTitleProgressCount.text = dashboardResponse.inProgress.total.toString()

    }


    object MockDashboard {

        val dashboardResponse = DashboardData(
            user = User(
                name = "Livia Vaccaro",
                profileImageUrl = "https://yourserver.com/images/livia.png",
                notificationEnabled = true
            ),
            todayTaskSummary = TodayTaskSummary(
                title = "Your today’s task almost done!",
                progressPercentage = 85,
                ctaText = "View Task"
            ),
            inProgress = InProgressSection(
                total = 6,
                tasks = listOf(
                    InProgressTask(
                        projectType = "Office Project",
                        taskTitle = "Grocery shopping app design",
                        progress = 70,
                        color = "#4B75F2",
                        bColor= "#2C4B75F2",
                        iconUrl = "https://yourserver.com/icons/office_project.png"
                    ),
                    InProgressTask(
                        projectType = "Personal Project",
                        taskTitle = "Uber Eats redesign challenge",
                        progress = 30,
                        color = "#FF9B6A",
                        bColor= "#6AFDCBFF",
                        iconUrl = "https://yourserver.com/icons/personal_project.png"
                    ), InProgressTask(
                        projectType = "Office Project",
                        taskTitle = "Grocery shopping app design",
                        progress = 70,
                        color = "#4BFFF2",
                        bColor= "#2C4FFFF2",
                        iconUrl = "https://yourserver.com/icons/office_project.png"
                    ),
                )
            ),
            taskGroups = TaskGroupsSection(
                total = 4,
                groups = listOf(
                    TaskGroup(
                        name = "Office Project",
                        taskCount = 23,
                        progress = 70,
                        color = "#FDCBFF",
                        bColor= "#6AFDCBFF",
                        iconUrl = "https://yourserver.com/icons/office_project.png"
                    ),
                    TaskGroup(
                        name = "Personal Project",
                        taskCount = 30,
                        progress = 52,
                        color = "#C9C5FF",
                        bColor= "#6AFDCBFF",
                        iconUrl = "https://yourserver.com/icons/personal_project.png"
                    ),
                    TaskGroup(
                        name = "Daily Study",
                        taskCount = 30,
                        progress = 87,
                        color = "#FFC88B",
                        bColor= "#2C4B75F2",
                        iconUrl = "https://yourserver.com/icons/daily_study.png"
                    )
                )
            )
        )
    }








    private fun setParamDashBoard(){
        viewModel.getDashboardDetails()
        setupObserver()

    }

    private fun setupObserver() {
        viewModel.getDashLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ApiState.Loading -> { /* show loading */ }
                is ApiState.Success -> { /* update UI with state.data */ }
                is ApiState.Error -> { /* show error */ }
            }
        }
    }


    private fun setRecycleViewProgress(){
        baseAdapter  = BaseAdapterBinding(
            requireContext(),
            arrayListOf(),
            this,
            R.layout.item_progress
        )
        binding.rvProgress.adapter = baseAdapter
    }

    override fun onBind(holder: BaseAdapterBinding.DataBindingViewHolder, position: Int) {
        (holder.binding as ItemProgressBinding).apply {
            itemProgress = baseAdapter.getItem(position)
                        try {
                val colorInt = baseAdapter.getItem(position).color.toColorInt()
                progressBar.setIndicatorColor(colorInt)
                card.setCardBackgroundColor(baseAdapter.getItem(position).bColor.toColorInt())

            } catch (_: Exception) {
            }
//            Glide.with(requireContext())
//                .load(baseAdapter.getItem(position).iconUrl)
//                .placeholder(R.drawable.ic_outline_cases) // shown while loading
//                .error(R.drawable.ic_notifications_black_24dp)             // shown on error
//                .into(ivProjectIcon)
        }
    }


    private fun setRecycleViewTask(){
        baseAdapterTwo  = BaseAdapterBindingTwo(
            requireContext(),
            arrayListOf(),
            this,
            R.layout.item_task_group
        )
        binding.rvTask.adapter = baseAdapterTwo
    }

    override fun onBindTwo(holder: BaseAdapterBindingTwo.DataBindingViewHolder, position: Int) {
        (holder.binding as ItemTaskGroupBinding).apply {
            itemTask = baseAdapterTwo.getItem(position)

            try {
                val colorInt = baseAdapter.getItem(position).color.toColorInt()
                progressCircle.setIndicatorColor(colorInt)
                card.setCardBackgroundColor(baseAdapter.getItem(position).bColor.toColorInt())
            } catch (_: Exception) {
            }



//            Glide.with(requireContext())
//                .load(baseAdapter.getItem(position).iconUrl)
//                .placeholder(R.drawable.ic_outline_cases) // shown while loading
//                .error(R.drawable.ic_notifications_black_24dp)             // shown on error
//                .into(ivIcon)
        }
    }

}