package com.example.habitualize.utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.habitualize.ui.models.AppTaskModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


val languageCodeList = arrayListOf(
    "en",
    "ar",
    "de",
    "fil",
    "fr",
    "hi",
    "id",
    "it",
    "ja",
    "pt",
    "ru",
    "tr"
)
val languagesList = arrayListOf(
    "English",
    "العربية",
    "Deutsch",
    "Filipino",
    "Français",
    "हिन्दी",
    "Bahasa Indonesia",
    "Italiano",
    "日本語",
    "Português",
    "Русский",
    "Türkçe"
)

val appTaskList_en = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Open App 5 times and earn 50 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Complete Total of 5 Tasks and earn 50 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Add Total of 10 Tasks and earn 50 Coins ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Open Total of 10 Tasks and earn 40 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Add Total 5 Community Challenges and earn 20 Coins ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Open Total of 15 Tasks and earn 75 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Open App 15 times and earn 100 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Complete Total of 25 Tasks and earn 100 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Scratch 20 gratitude and earn 20 Coins ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Complete Total of 50 Tasks and earn 200 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Open 5 Tasks and earn 50 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Share App with 40 peoples and earn 30 Coins ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Complete Total of 150 Tasks and earn 250 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Open Total of 50 Tasks and earn 150 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Add Total of 30 Tasks and earn 100 Coins ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Open App 50 times and earn 200 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Add Total 20 Community Challenges and earn 50 Coins ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Share App with 20 peoples and earn 20 Coins ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Purchase 5 themes and earn 40 Coins ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Purchase 10 themes and earn 60 Coins ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Scratch 50 gratitude and earn 50 Coins ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_ar = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "افتح التطبيق 5 مرات واكسب 50 قطعة نقدية ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "أكمل إجمالي 5 مهام واربح 50 قطعة نقدية ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "أضف إجمالي 10 مهام واكسب 50 قطعة نقدية ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "افتح إجمالي 10 مهام واكسب 40 قطعة نقدية ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "أضف إجمالي 5 تحديات مجتمعية واكسب 20 قطعة نقدية ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "افتح إجمالي 15 مهمة واكسب 75 قطعة نقدية ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "افتح التطبيق 15 مرة واكسب 100 عملة معدنية ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "أكمل إجمالي 25 مهمة واربح 100 قطعة نقدية ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "خدش 20 امتنان واكسب 20 عملة معدنية ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "أكمل إجمالي 50 مهمة واكسب 200 قطعة نقدية ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "افتح 5 مهام واكسب 50 قطعة نقدية ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "شارك التطبيق مع 40 شخصًا واكسب 30 عملة معدنية ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "أكمل إجمالي 150 مهمة واربح 250 قطعة نقدية ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "افتح إجمالي 50 مهمة واربح 150 قطعة نقدية ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "أضف إجمالي 30 مهمة واكسب 100 قطعة نقدية ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "افتح التطبيق 50 مرة واكسب 200 عملة معدنية ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "أضف إجمالي 20 من تحديات المجتمع واكسب 50 قطعة نقدية ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "شارك التطبيق مع 20 شخصًا واكسب 20 عملة معدنية ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "قم بشراء 5 سمات واكسب 40 قطعة نقدية ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "قم بشراء 10 سمات واكسب 60 قطعة نقدية ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "اخدش 50 امتنانًا واكسب 50 قطعة نقدية ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_de = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Öffnen Sie die App 5 Mal und verdienen Sie 50 Münzen ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Schließe insgesamt 5 Aufgaben ab und verdiene 50 Münzen ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Fügen Sie insgesamt 10 Aufgaben hinzu und verdienen Sie 50 Münzen ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Öffne insgesamt 10 Aufgaben und verdiene 40 Münzen ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Fügen Sie insgesamt 5 Community-Herausforderungen hinzu und verdienen Sie 20 Münzen ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Öffne insgesamt 15 Aufgaben und verdiene 75 Münzen ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Öffnen Sie die App 15 Mal und verdienen Sie 100 Münzen ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Schließe insgesamt 25 Aufgaben ab und verdiene 100 Münzen ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Rubbeln Sie 20 Dankbarkeit und verdienen Sie 20 Münzen ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Schließe insgesamt 50 Aufgaben ab und verdiene 200 Münzen ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Öffne 5 Aufgaben und verdiene 50 Münzen ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Teilen Sie die App mit 40 Personen und verdienen Sie 30 Münzen ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Schließe insgesamt 150 Aufgaben ab und verdiene 250 Münzen ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Öffne insgesamt 50 Aufgaben und verdiene 150 Münzen ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Fügen Sie insgesamt 30 Aufgaben hinzu und verdienen Sie 100 Münzen ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Öffnen Sie die App 50 Mal und verdienen Sie 200 Münzen ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Fügen Sie insgesamt 20 Community-Herausforderungen hinzu und verdienen Sie 50 Münzen ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Teilen Sie die App mit 20 Personen und verdienen Sie 20 Münzen ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Kaufen Sie 5 Themen und verdienen Sie 40 Münzen ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Kaufen Sie 10 Themen und verdienen Sie 60 Münzen ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Rubbeln Sie 50 Dankbarkeit und verdienen Sie 50 Münzen ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_fil = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Buksan ang App 5 beses at kumita ng 50 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Kumpletuhin ang Kabuuan ng 5 Gawain at kumita ng 50 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Magdagdag ng Kabuuan ng 10 Gawain at kumita ng 50 Coins ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Buksan ang Kabuuan ng 10 Gawain at kumita ng 40 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Magdagdag ng Kabuuang 5 Hamon sa Komunidad at kumita ng 20 Coins ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Buksan ang Kabuuan ng 15 Gawain at kumita ng 75 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Buksan ang App ng 15 beses at kumita ng 100 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Kumpletuhin ang Kabuuan ng 25 Gawain at kumita ng 100 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Mag-scratch ng 20 pasasalamat at kumita ng 20 Coins ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Kumpletuhin ang Kabuuan ng 50 Gawain at kumita ng 200 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Magbukas ng 5 Gawain at kumita ng 50 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Ibahagi ang App sa 40 tao at kumita ng 30 Coins ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Kumpletuhin ang Kabuuan ng 150 Gawain at kumita ng 250 Coins ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Buksan ang Kabuuan ng 50 Gawain at kumita ng 150 Coins ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Magdagdag ng Kabuuan ng 30 Gawain at kumita ng 100 Coins ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Buksan ang App ng 50 beses at kumita ng 200 Coins ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Magdagdag ng Kabuuang 20 Hamon sa Komunidad at kumita ng 50 Coins ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Ibahagi ang App sa 20 tao at kumita ng 20 Coins ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Bumili ng 5 tema at kumita ng 40 Coins ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Bumili ng 10 tema at kumita ng 60 Coins ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Mag-scratch ng 50 pasasalamat at kumita ng 50 Coins ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_fr = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Ouvrez l'application 5 fois et gagnez 50 pièces ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Terminez un total de 5 tâches et gagnez 50 pièces ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Ajoutez un total de 10 tâches et gagnez 50 pièces ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Ouvrez un total de 10 tâches et gagnez 40 pièces ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Ajoutez un total de 5 défis communautaires et gagnez 20 pièces ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Ouvrez un total de 15 tâches et gagnez 75 pièces ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Ouvrez l'application 15 fois et gagnez 100 pièces ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Terminez un total de 25 tâches et gagnez 100 pièces ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Grattez 20 gratitude et gagnez 20 pièces ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Terminez un total de 50 tâches et gagnez 200 pièces ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Ouvrez 5 tâches et gagnez 50 pièces ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Partagez l'application avec 40 personnes et gagnez 30 pièces ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Terminez un total de 150 tâches et gagnez 250 pièces ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Ouvrez un total de 50 tâches et gagnez 150 pièces ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Ajoutez un total de 30 tâches et gagnez 100 pièces ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Ouvrez l'application 50 fois et gagnez 200 pièces ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Ajoutez un total de 20 défis communautaires et gagnez 50 pièces ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Partagez l'application avec 20 personnes et gagnez 20 pièces ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Achetez 5 thèmes et gagnez 40 pièces ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Achetez 10 thèmes et gagnez 60 pièces ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Grattez 50 points de gratitude et gagnez 50 pièces ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_hi = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "ऐप को 5 बार खोलें और 50 कॉइन कमाएं ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "कुल 5 कार्य पूरे करें और 50 सिक्के अर्जित करें ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "कुल 10 कार्य जोड़ें और 50 सिक्के अर्जित करें ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "कुल 10 कार्य खोलें और 40 सिक्के अर्जित करें ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "कुल 5 सामुदायिक चुनौतियाँ जोड़ें और 20 सिक्के अर्जित करें ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "कुल 15 कार्य खोलें और 75 सिक्के अर्जित करें ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "ऐप को 15 बार खोलें और 100 कॉइन कमाएं ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "कुल 25 कार्य पूरे करें और 100 सिक्के अर्जित करें ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "20 आभार स्क्रैच करें और 20 सिक्के अर्जित करें ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "कुल 50 कार्य पूरे करें और 200 सिक्के अर्जित करें ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "5 कार्य खोलें और 50 सिक्के अर्जित करें ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "40 लोगों के साथ ऐप शेयर करें और 30 सिक्के कमाएं ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "कुल 150 कार्य पूरे करें और 250 सिक्के अर्जित करें ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "कुल 50 कार्य खोलें और 150 सिक्के अर्जित करें ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "कुल 30 कार्य जोड़ें और 100 सिक्के अर्जित करें ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "ऐप को 50 बार खोलें और 200 कॉइन कमाएं ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "कुल 20 सामुदायिक चुनौतियाँ जोड़ें और 50 सिक्के अर्जित करें ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "20 लोगों के साथ ऐप शेयर करें और 20 सिक्के कमाएं ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "5 थीम खरीदें और 40 कॉइन अर्जित करें ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "10 थीम खरीदें और 60 कॉइन अर्जित करें ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "50 आभार स्क्रैच करें और 50 सिक्के अर्जित करें ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_id = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Buka Aplikasi 5 kali dan dapatkan 50 Koin ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Selesaikan Total 5 Tugas dan dapatkan 50 Koin ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Tambahkan Total 10 Tugas dan dapatkan 50 Koin ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Buka Total 10 Tugas dan dapatkan 40 Koin ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Tambahkan Total 5 Tantangan Komunitas dan dapatkan 20 Koin ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Buka Total 15 Tugas dan dapatkan 75 Koin ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Buka Aplikasi 15 kali dan dapatkan 100 Koin ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Selesaikan Total 25 Tugas dan dapatkan 100 Koin ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Gosok 20 rasa terima kasih dan dapatkan 20 Koin ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Selesaikan Total 50 Tugas dan dapatkan 200 Koin ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Buka 5 Tugas dan dapatkan 50 Koin ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Bagikan Aplikasi dengan 40 orang dan dapatkan 30 Koin ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Selesaikan Total 150 Tugas dan dapatkan 250 Koin ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Buka Total 50 Tugas dan dapatkan 150 Koin ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Tambahkan Total 30 Tugas dan dapatkan 100 Koin ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Buka Aplikasi 50 kali dan dapatkan 200 Koin ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Tambahkan Total 20 Tantangan Komunitas dan dapatkan 50 Koin ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Bagikan Aplikasi dengan 20 orang dan dapatkan 20 Koin ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Beli 5 tema dan dapatkan 40 Koin ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Beli 10 tema dan dapatkan 60 Koin ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Gosok 50 rasa terima kasih dan dapatkan 50 Koin ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_it = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Apri l'app 5 volte e guadagna 50 monete ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Completa un totale di 5 compiti e guadagna 50 monete ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Aggiungi un totale di 10 attività e guadagna 50 monete ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Apri un totale di 10 attività e guadagna 40 monete ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Aggiungi un totale di 5 sfide della community e guadagna 20 monete ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Apri un totale di 15 attività e guadagna 75 monete ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Apri l'app 15 volte e guadagna 100 monete ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Completa un totale di 25 compiti e guadagna 100 monete ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Gratta 20 gratitudine e guadagna 20 monete ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Completa un totale di 50 compiti e guadagna 200 monete ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Apri 5 attività e guadagna 50 monete ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Condividi l'app con 40 persone e guadagna 30 monete ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Completa un totale di 150 compiti e guadagna 250 monete ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Apri un totale di 50 attività e guadagna 150 monete ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Aggiungi un totale di 30 attività e guadagna 100 monete ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Apri l'app 50 volte e guadagna 200 monete ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Aggiungi un totale di 20 sfide della community e guadagna 50 monete ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Condividi l'app con 20 persone e guadagna 20 monete ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Acquista 5 temi e guadagna 40 monete ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Acquista 10 temi e guadagna 60 monete ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Gratta 50 gratitudine e guadagna 50 monete ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_ja = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "アプリを 5 回開いて 50 コインを獲得 ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "合計 5 つのタスクを完了して 50 コインを獲得する ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "合計 10 個のタスクを追加して 50 コインを獲得 ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "合計 10 のタスクを開き、40 コインを獲得する ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "合計 5 つのコミュニティ チャレンジを追加して 20 コインを獲得 ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "合計 15 のタスクを開き、75 コインを獲得する ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "アプリを 15 回開いて 100 コインを獲得 ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "合計 25 のタスクを完了して 100 コインを獲得する ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "感謝の気持ちを 20 回スクラッチして 20 コインを獲得 ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "合計 50 のタスクを完了して 200 コインを獲得する ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "5 つのタスクを開いて 50 コインを獲得 ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "アプリを 40 人と共有して 30 コインを獲得 ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "合計 150 のタスクを完了して 250 コインを獲得する ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "合計 50 のタスクを開き、150 コインを獲得する ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "合計 30 個のタスクを追加して 100 コインを獲得 ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "アプリを 50 回開いて 200 コインを獲得 ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "合計 20 のコミュニティ チャレンジを追加して 50 コインを獲得 ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "アプリを 20 人と共有して 20 コインを獲得 ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "5 つのテーマを購入して 40 コインを獲得 ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "テーマを 10 個購入して 60 コインを獲得 ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "50 感謝をスクラッチして 50 コインを獲得 ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_pt = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Abra o aplicativo 5 vezes e ganhe 50 moedas ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Complete um total de 5 tarefas e ganhe 50 moedas ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Adicione um total de 10 tarefas e ganhe 50 moedas ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Abra um total de 10 tarefas e ganhe 40 moedas ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Adicione um total de 5 desafios da comunidade e ganhe 20 moedas ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Abra um total de 15 tarefas e ganhe 75 moedas ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Abra o aplicativo 15 vezes e ganhe 100 moedas ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Complete um total de 25 tarefas e ganhe 100 moedas ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Raspe 20 de gratidão e ganhe 20 Moedas ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Complete um total de 50 tarefas e ganhe 200 moedas ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Abra 5 Tarefas e ganhe 50 Moedas ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Compartilhe o aplicativo com 40 pessoas e ganhe 30 moedas ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Complete um total de 150 tarefas e ganhe 250 moedas ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Abra um total de 50 tarefas e ganhe 150 moedas ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Adicione um total de 30 tarefas e ganhe 100 moedas ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Abra o aplicativo 50 vezes e ganhe 200 moedas ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Adicione um total de 20 desafios da comunidade e ganhe 50 moedas ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Compartilhe o aplicativo com 20 pessoas e ganhe 20 moedas ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Compre 5 temas e ganhe 40 moedas ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Compre 10 temas e ganhe 60 moedas ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Raspe 50 de gratidão e ganhe 50 moedas ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_ru = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Откройте приложение 5 раз и заработайте 50 монет ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Выполните в общей сложности 5 заданий и заработайте 50 монет. ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Добавьте в общей сложности 10 задач и заработайте 50 монет ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Откройте Всего 10 Заданий и заработайте 40 Монет ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Добавьте всего 5 испытаний сообщества и заработайте 20 монет ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Откройте Всего 15 Заданий и заработайте 75 Монет ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Откройте приложение 15 раз и заработайте 100 монет ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Выполните в общей сложности 25 заданий и заработайте 100 монет. ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Сотрите 20 благодарностей и заработайте 20 монет ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Выполните в общей сложности 50 заданий и заработайте 200 монет. ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Откройте 5 заданий и заработайте 50 монет ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Поделитесь приложением с 40 людьми и заработайте 30 монет ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Выполните в общей сложности 150 заданий и заработайте 250 монет. ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Откройте Всего 50 Заданий и заработайте 150 Монет ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Добавьте 30 заданий и заработайте 100 монет. ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Откройте приложение 50 раз и заработайте 200 монет ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Добавьте 20 испытаний сообщества и заработайте 50 монет. ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Поделитесь приложением с 20 людьми и заработайте 20 монет ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "Купите 5 тем и заработайте 40 монет ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "Купите 10 тем и заработайте 60 монет. ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "Сотрите 50 благодарностей и заработайте 50 монет ",
        type = gratitudeScratchCounter
    ),
)

val appTaskList_tr = arrayListOf<AppTaskModel>(
    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Uygulamayı 5 kez açın ve 50 Jeton kazanın ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "Toplam 5 Görevi Tamamlayın ve 50 Jeton kazanın ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 10,
        reward_text = "Toplam 10 Görev ekleyin ve 50 Jeton kazanın ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 10,
        reward_text = "Toplam 10 Görev açın ve 40 Jeton kazanın ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 5,
        reward_text = "Toplam 5 Topluluk Mücadelesi ekleyin ve 20 Jeton kazanın ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 70,
        target = 15,
        reward_text = "Toplam 15 Görev açın ve 75 Jeton kazanın ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 15,
        reward_text = "Uygulamayı 15 kez açın ve 100 Jeton kazanın ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 25,
        reward_text = "Toplam 25 Görevi Tamamlayın ve 100 Jeton Kazanın ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "20 şükran kazıyın ve 20 Jeton kazanın ",
        type = gratitudeScratchCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Toplam 50 Görevi Tamamlayın ve 200 Jeton kazanın ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 5,
        reward_text = "5 Görev açın ve 50 Jeton kazanın ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 30,
        target = 40,
        reward_text = "Uygulamayı 40 kişiyle paylaşın ve 30 Jeton kazanın ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 250,
        target = 150,
        reward_text = "Toplam 150 Görevi Tamamlayın ve 250 Jeton Kazanın ",
        type = completeTaskCounter
    ),

    AppTaskModel(
        reward = 150,
        target = 50,
        reward_text = "Toplam 50 Görev açın ve 150 Jeton kazanın ",
        type = openTaskCounter
    ),

    AppTaskModel(
        reward = 100,
        target = 30,
        reward_text = "Toplam 30 Görev ekleyin ve 100 Jeton kazanın ",
        type = addTaskCounter
    ),

    AppTaskModel(
        reward = 200,
        target = 50,
        reward_text = "Uygulamayı 50 kez açın ve 200 Jeton kazanın ",
        type = openAppCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 20,
        reward_text = "Toplam 20 Topluluk Mücadelesi ekleyin ve 50 Jeton kazanın ",
        type = addCommunityChallengeCounter
    ),

    AppTaskModel(
        reward = 20,
        target = 20,
        reward_text = "Uygulamayı 20 kişiyle paylaşın ve 20 Jeton kazanın ",
        type = shareAppCounter
    ),

    AppTaskModel(
        reward = 40,
        target = 5,
        reward_text = "5 tema satın alın ve 40 Jeton kazanın ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 60,
        target = 10,
        reward_text = "10 tema satın alın ve 60 Jeton kazanın ",
        type = purchaseThemeCounter
    ),

    AppTaskModel(
        reward = 50,
        target = 50,
        reward_text = "50 şükran kazıyın ve 50 Jeton kazanın ",
        type = gratitudeScratchCounter
    ),
)


fun getFormattedTimeWithDate(date: Long): String{
    val formatter: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(Date(date))
}

fun getFormattedTime(date: Long): String{
    val formatter: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(Date(date))
}

fun hideKeyboard(activity: Activity, view: View) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun calculateTimeDiff(lastUpdatedTime: Long): Long{
    var diff: Long = Calendar.getInstance().timeInMillis - lastUpdatedTime
    var seconds: Long = diff / 1000
    var minutes: Long = seconds / 60
    var hours: Long = minutes / 60
    var days: Long = hours / 24
    return days
}

fun changeStatusBarColor(activity: Activity,color: Int){
    try {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, color)
    }catch (e: Exception){
        Log.e("ViewUtils","Error in changing the theme: ${e.localizedMessage}")
    }
}

fun convertHexaColorToRGB(context: Context ,color: Int): Int{
    val color: Int = ContextCompat.getColor(context, color)
    val red = (color shr 16 and 0xFF)
    val green = (color shr 8 and 0xFF)
    val blue = (color and 0xFF)
    val alpha = (color shr 24 and 0xFF).toFloat()
    println("color:: ${Color.rgb(red, green, blue)}")
    return Color.rgb(red, green, blue)
}












