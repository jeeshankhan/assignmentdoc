package com.video.docquityandroidassignment.model

data class DashboardResponse(
    val message:String?=null,
    val status:Boolean,
    val dataDash:DashboardData

)

data class DashboardData(
    val user: User,
    val todayTaskSummary: TodayTaskSummary,
    val inProgress: InProgressSection,
    val taskGroups: TaskGroupsSection,
        )
data class User(
    val name: String,
    val profileImageUrl: String,
    val notificationEnabled: Boolean
)
data class TodayTaskSummary(
    val title: String,
    val progressPercentage: Int,
    val ctaText: String
)
data class InProgressSection(
    val total: Int,
    val tasks: List<InProgressTask>
)

data class InProgressTask(
    val projectType: String,
    val taskTitle: String,
    val progress: Int,
    val color: String,
    val iconUrl: String
)
data class TaskGroupsSection(
    val total: Int,
    val groups: List<TaskGroup>
)

data class TaskGroup(
    val name: String,
    val taskCount: Int,
    val progress: Int,
    val color: String,
    val iconUrl: String
)



