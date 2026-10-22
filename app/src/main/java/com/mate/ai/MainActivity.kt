package com.mate.ai


package com.studymateai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Purple = Color(0xFF6554F0)
private val Background = Color(0xFFF4F5FF)
private val Dark = Color(0xFF171B3D)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyMateApp()
        }
    }
}

@Composable
fun StudyMateApp() {
    var page by remember { mutableStateOf("الرئيسية") }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Purple,
            background = Background
        )
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar {
                    val pages = listOf(
                        "الرئيسية",
                        "المساعد",
                        "الخطة",
                        "مكتبتي"
                    )

                    pages.forEach { item ->
                        NavigationBarItem(
                            selected = page == item,
                            onClick = { page = item },
                            icon = {
                                Icon(
                                    imageVector = when (item) {
                                        "الرئيسية" -> Icons.Default.Home
                                        "المساعد" -> Icons.Default.SmartToy
                                        "الخطة" -> Icons.Default.CalendarMonth
                                        else -> Icons.Default.Folder
                                    },
                                    contentDescription = item
                                )
                            },
                            label = { Text(item) }
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (page) {
                    "الرئيسية" -> HomeScreen { page = it }
                    "المساعد" -> ChatScreen()
                    "الخطة" -> PlanScreen()
                    "مكتبتي" -> LibraryScreen()
                    "التلخيص" -> SummaryScreen()
                    "الاختبارات" -> QuizScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(openPage: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "StudyMate AI",
                color = Purple,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "أهلاً بيك 👋",
                fontSize = 29.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Dark
            )

            Text(
                "ذاكر بذكاء واستعد لامتحاناتك بسهولة",
                color = Color.Gray
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Dark
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(Modifier.padding(22.dp)) {
                    Text(
                        "مذاكرتك، بطريقة أذكى ✨",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "اسأل المساعد الذكي، لخص محاضراتك، ونظم وقتك.",
                        color = Color.LightGray
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { openPage("المساعد") }
                    ) {
                        Text("ابدأ المذاكرة")
                    }
                }
            }
        }

        item {
            Text(
                "أدوات المذاكرة",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureCard(
                    "محادثة ذكية",
                    "اسأل عن أي موضوع",
                    Icons.Default.Chat,
                    Modifier.fillMaxWidth()
                ) { openPage("المساعد") }

                FeatureCard(
                    "تلخيص المحاضرات",
                    "حوّل المحاضرات لنقاط",
                    Icons.Default.Description,
                    Modifier.fillMaxWidth()
                ) { openPage("التلخيص") }

                FeatureCard(
                    "اختبارات تفاعلية",
                    "اختبر معلوماتك",
                    Icons.Default.Quiz,
                    Modifier.fillMaxWidth()
                ) { openPage("الاختبارات") }

                FeatureCard(
                    "خطة المذاكرة",
                    "تابع مهامك اليومية",
                    Icons.Default.CalendarMonth,
                    Modifier.fillMaxWidth()
                ) { openPage("الخطة") }
            }
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Purple,
                modifier = Modifier.size(34.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(description, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun ChatScreen() {
    var question by remember { mutableStateOf("") }

    var messages by remember {
        mutableStateOf(
            listOf(
                "المساعد: أهلاً بيك! أنا StudyMate AI 🤖",
                "المساعد: اكتب سؤالك الدراسي هنا."
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "المساعد الذكي 🤖",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages.size) { index ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        messages[index],
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = question,
                onValueChange = { question = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                decorationBox = { inner ->
                    if (question.isEmpty()) {
                        Text(
                            "اكتب سؤالك هنا...",
                            color = Color.Gray
                        )
                    }
                    inner()
                }
            )

            IconButton(
                onClick = {
                    if (question.isNotBlank()) {
                        messages = messages + "أنت: $question"
                        messages = messages +
                            "المساعد: تم استلام سؤالك. لعرض إجابة AI حقيقية، يجب ربط التطبيق بخادم ذكاء اصطناعي."
                        question = ""
                    }
                }
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "إرسال",
                    tint = Purple
                )
            }
        }

        Text(
            "نسخة تجريبية: المساعد غير متصل بالذكاء الاصطناعي بعد.",
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun SummaryScreen() {
    var lecture by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Text(
            "تلخيص المحاضرات",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = lecture,
            onValueChange = { lecture = it },
            label = { Text("اكتب نص المحاضرة") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { result = lecture.isNotBlank() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("إنشاء ملخص تجريبي")
        }

        if (result) {
            Spacer(Modifier.height(16.dp))
            Text(
                "تم إدخال المحاضرة بنجاح.\n" +
                "لإنشاء ملخص حقيقي، اربط الصفحة بخدمة AI.",
                color = Dark
            )
        }
    }
}

@Composable
fun QuizScreen() {
    var selected by remember { mutableIntStateOf(-1) }
    var checked by remember { mutableStateOf(false) }

    Column(Modifier.padding(18.dp)) {
        Text(
            "اختبر معلوماتك 📝",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        Text("ما أفضل طريقة لتنظيم المذاكرة؟")

        val answers = listOf(
            "تأجيل المهام",
            "جدول بأهداف واضحة",
            "المذاكرة دون راحة"
        )

        answers.forEachIndexed { index, answer ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selected == index,
                    onClick = {
                        selected = index
                        checked = false
                    }
                )
                Text(answer)
            }
        }

        Button(
            onClick = { checked = true },
            enabled = selected >= 0
        ) {
            Text("تحقق من الإجابة")
        }

        if (checked) {
            Text(
                if (selected == 1)
                    "إجابة صحيحة! أحسنت 🎉"
                else
                    "حاول مرة أخرى."
            )
        }
    }
}

@Composable
fun PlanScreen() {
    val tasks = listOf(
        "مراجعة المحاضرة الأولى",
        "حل أسئلة التدريب",
        "مراجعة الملخص",
        "الاستعداد للاختبار"
    )

    val completed = remember {
        mutableStateListOf(false, false, false, false)
    }

    Column(Modifier.padding(18.dp)) {
        Text(
            "خطة المذاكرة 📅",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text("المهام المكتملة: ${completed.count { it }} من ${tasks.size}")

        LinearProgressIndicator(
            progress = { completed.count { it }.toFloat() / tasks.size },
            modifier = Modifier.fillMaxWidth()
        )

        tasks.forEachIndexed { index, task ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = completed[index],
                    onCheckedChange = { completed[index] = it }
                )
                Text(task)
            }
        }
    }
}

@Composable
fun LibraryScreen() {
    val files = listOf(
        "المحاضرات",
        "الملخصات",
        "الاختبارات",
        "البطاقات التعليمية",
        "الملاحظات"
    )

    Column(Modifier.padding(18.dp)) {
        Text(
            "مكتبتي 📚",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        files.forEach { file ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    file,
                    modifier = Modifier.padding(18.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
