package com.crypto.tracker

import androidx.lifecycle.LifecycleObserver
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.repository.ProjectRepository


class MainViewModel(private val repository: ProjectRepository) : BaseViewModel(), LifecycleObserver {

}