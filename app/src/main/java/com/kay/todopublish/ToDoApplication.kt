package com.kay.todopublish

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication : Application()

/**
 *  I will just put general notes here:
 *
 *  You have a lot of leftover comments hanging around that you want to clean up. The comments that
 *  say things for you to remember are fine, but commented out code looks sloppy. So I would go file
 *  by file and make sure it all looks clean.
 *
 *  Your gradle files are mostly ok, but you should do a better job of keeping everything updated. You
 *  have a pretty old kotlin version and compose version at this point. And accompanist navigation animations
 *  is defined twice. But i don't want to go too deeply into gradle stuff haha...
 *
 *  I also still think the top bars in this app are really confusing haha. But they work
 *  and there is nothing super wrong about them, it is just a bit complicated.
 *
 */
