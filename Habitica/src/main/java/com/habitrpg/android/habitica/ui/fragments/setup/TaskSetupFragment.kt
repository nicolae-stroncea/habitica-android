package com.habitrpg.android.habitica.ui.fragments.setup

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.habitrpg.android.habitica.R
import com.habitrpg.android.habitica.components.AppComponent
import com.habitrpg.android.habitica.extensions.inflate
import com.habitrpg.android.habitica.extensions.notNull
import com.habitrpg.android.habitica.models.tasks.Days
import com.habitrpg.android.habitica.models.tasks.Task
import com.habitrpg.android.habitica.models.user.User
import com.habitrpg.android.habitica.ui.AvatarView
import com.habitrpg.android.habitica.ui.SpeechBubbleView
import com.habitrpg.android.habitica.ui.activities.SetupActivity
import com.habitrpg.android.habitica.ui.adapter.setup.TaskSetupAdapter
import com.habitrpg.android.habitica.ui.fragments.BaseFragment
import com.habitrpg.android.habitica.ui.helpers.bindView
import com.habitrpg.android.habitica.ui.helpers.resetViews
import java.util.*

class TaskSetupFragment : BaseFragment() {


    var activity: SetupActivity? = null
    var width: Int = 0
    private val recyclerView: RecyclerView? by bindView(R.id.recyclerView)
    private val avatarView: AvatarView? by bindView(R.id.avatarView)
    private val speechBubbleView: SpeechBubbleView? by bindView(R.id.speech_bubble)
    internal var adapter: TaskSetupAdapter = TaskSetupAdapter()
    private var taskGroups: Array<Array<String>> = arrayOf()
    private var tasks: Array<Array<Any>> = arrayOf()
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return container?.inflate(R.layout.fragment_setup_tasks)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetViews()

        this.setTasks()

        this.adapter = TaskSetupAdapter()
        this.adapter.setTaskList(this.taskGroups)
        this.recyclerView?.layoutManager = GridLayoutManager(activity, 2)
        this.recyclerView?.adapter = this.adapter

        if (this.user != null) {
            this.updateAvatar()
        }

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && context != null) {
            speechBubbleView?.animateText(context?.getString(R.string.task_setup_description) ?: "")
        }
    }

    fun setUser(user: User?) {
        this.user = user
        if (avatarView != null) {
            updateAvatar()
        }
    }

    private fun updateAvatar() {
        user.notNull {
            avatarView?.setAvatar(it)
        }
    }

    override fun injectFragment(component: AppComponent) {
        component.inject(this)
    }

    private fun setTasks() {
        this.taskGroups = arrayOf(arrayOf(getString(R.string.setup_group_work), "work"), arrayOf(getString(R.string.setup_group_exercise), "exercise"), arrayOf(getString(R.string.setup_group_health), "healthWellness"), arrayOf(getString(R.string.setup_group_school), "school"), arrayOf(getString(R.string.setup_group_teams), "teams"), arrayOf(getString(R.string.setup_group_chores), "chores"), arrayOf(getString(R.string.setup_group_creativity), "creativity"), arrayOf(getString(R.string.setuP_group_other), "other"))

        this.tasks = arrayOf(arrayOf("work", "habit", getString(R.string.setup_task_work_1), true, false), arrayOf<Any>("work", "daily", getString(R.string.setup_task_work_2)), arrayOf<Any>("work", "todo", getString(R.string.setup_task_work_3)),
                arrayOf("exercise", "habit", getString(R.string.setup_task_exercise_1), true, false), arrayOf<Any>("exercise", "daily", getString(R.string.setup_task_exercise_2)), arrayOf<Any>("exercise", "todo", getString(R.string.setup_task_exercise_3)),
                arrayOf("healthWellness", "habit", getString(R.string.setup_task_healthWellness_1), true, true), arrayOf<Any>("healthWellness", "daily", getString(R.string.setup_task_healthWellness_2)), arrayOf<Any>("healthWellness", "todo", getString(R.string.setup_task_healthWellness_3)),
                arrayOf("school", "habit", getString(R.string.setup_task_school_1), true, true), arrayOf<Any>("school", "daily", getString(R.string.setup_task_school_2)), arrayOf<Any>("school", "todo", getString(R.string.setup_task_school_3)),
                arrayOf("teams", "habit", getString(R.string.setup_task_teams_1), true, false), arrayOf<Any>("teams", "daily", getString(R.string.setup_task_teams_2)), arrayOf<Any>("teams", "todo", getString(R.string.setup_task_teams_3)),
                arrayOf("chores", "habit", getString(R.string.setup_task_chores_1), true, false), arrayOf<Any>("chores", "daily", getString(R.string.setup_task_chores_2)), arrayOf<Any>("chores", "todo", getString(R.string.setup_task_chores_3)),
                arrayOf("creativity", "habit", getString(R.string.setup_task_creativity_1), true, false), arrayOf<Any>("creativity", "daily", getString(R.string.setup_task_creativity_2)), arrayOf<Any>("creativity", "todo", getString(R.string.setup_task_creativity_3)))
    }

    fun createSampleTasks(): List<Task> {
        val groups = ArrayList<String>()
        for ((i, checked) in this.adapter.checkedList.withIndex()) {
            if (checked) {
                groups.add(this.taskGroups[i][1])
            }
        }
        val tasks = ArrayList<Task>()
        for (task in this.tasks) {
            val taskGroup = task[0] as String
            if (groups.contains(taskGroup)) {
                val taskObject: Task = if (task.size == 5) {
                    this.makeTaskObject(task[1] as String, task[2] as String, task[3] as Boolean, task[4] as Boolean)
                } else {
                    this.makeTaskObject(task[1] as String, task[2] as String, null, null)
                }
                tasks.add(taskObject)
            }
        }
        return tasks
    }

    private fun makeTaskObject(type: String, text: String, up: Boolean?, down: Boolean?): Task {
        val task = Task()
        task.text = text
        task.priority = 1.0f
        task.type = type

        if (type == "habit") {
            task.up = up
            task.down = down
        }

        if (type == "daily") {
            task.frequency = "weekly"
            task.startDate = Date()
            val days = Days()
            days.m = true
            days.t = true
            days.w = true
            days.th = true
            days.f = true
            days.s = true
            days.su = true
            task.repeat = days
        }

        return task
    }

}
