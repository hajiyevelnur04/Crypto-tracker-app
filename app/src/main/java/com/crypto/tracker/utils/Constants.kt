package com.crypto.tracker.utils

const val PREFS_NAME = "cryptoTracker"
const val KEY_INTRO = "intro"
const val KEY_SERVICE = "service"
const val TABLE_NAME = "cryptoTracker"
const val CHANNEL_ID = "1992"
const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

const val NOTIFICATION_CHANNEL_ID = "crypto_id"
const val NOTIFICATION_CHANNEL_NAME = "crypto_name"
const val NOTIFICATION_ID = 1
var LAST_SERVICES_CHECK: Long = 0
const val SERVICE_ALERTS_ID = "tracking_qaqa"
const val DATABASE_NAME = "cryptoTracker.db"
const val PRICE_LIST = "https://api.coingecko.com/api/v3/simple/price?ids="
const val PRICE_LIST_CURRENCY = "&vs_currencies="

// Name of Notification Channel for verbose notifications of background work
@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"

// The name of the image manipulation work
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Other keys
const val OUTPUT_PATH = "blur_filter_outputs"
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
const val TAG_OUTPUT = "OUTPUT"

const val DELAY_TIME_MILLIS: Long = 3000


