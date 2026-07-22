package com.muneeb.jarvis;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView statusText;
    private TextView resultText;
    private Button listenButton;

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    private static final int REQUEST_AUDIO = 100;
    private static final int SPLASH_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        showSplashScreen();

    }


    private void showSplashScreen() {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setGravity(
                Gravity.CENTER
        );

        layout.setPadding(
                30,
                30,
                30,
                30
        );

        TextView title =
                new TextView(this);

        title.setText(
                "JARVIS"
        );

        title.setTextSize(
                42
        );

        title.setGravity(
                Gravity.CENTER
        );

        TextView subtitle =
                new TextView(this);

        subtitle.setText(
                "Your Personal AI Assistant"
        );

        subtitle.setTextSize(
                20
        );

        subtitle.setGravity(
                Gravity.CENTER
        );

        TextView creator =
                new TextView(this);

        creator.setText(
                "\nCreated by\nLearn With Muneeb"
        );

        creator.setTextSize(
                18
        );

        creator.setGravity(
                Gravity.CENTER
        );

        layout.addView(
                title
        );

        layout.addView(
                subtitle
        );

        layout.addView(
                creator
        );

        setContentView(
                layout
        );


        new android.os.Handler().postDelayed(
                () -> {

                    createMainInterface();

                },
                SPLASH_TIME
        );

    }


    private void createMainInterface() {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        layout.setPadding(
                40,
                60,
                40,
                40
        );


        TextView title =
                new TextView(this);

        title.setText(
                "JARVIS"
        );

        title.setTextSize(
                32
        );

        title.setGravity(
                Gravity.CENTER
        );


        statusText =
                new TextView(this);

        statusText.setText(
                "JARVIS Ready"
        );

        statusText.setTextSize(
                22
        );

        statusText.setGravity(
                Gravity.CENTER
        );


        resultText =
                new TextView(this);

        resultText.setText(
                "Command ka intezar hai..."
        );

        resultText.setTextSize(
                18
        );

        resultText.setGravity(
                Gravity.CENTER
        );


        listenButton =
                new Button(this);

        listenButton.setText(
                "🎤 JARVIS se Baat Karein"
        );


        listenButton.setOnClickListener(
                v -> startListening()
        );


        layout.addView(
                title
        );

        layout.addView(
                statusText
        );

        layout.addView(
                resultText
        );

        layout.addView(
                listenButton
        );


        setContentView(
                layout
        );


        initializeVoice();


    }


    private void initializeVoice() {

        textToSpeech =
                new TextToSpeech(
                        this,
                        status -> {

                            if (
                                    status ==
                                    TextToSpeech.SUCCESS
                            ) {

                                textToSpeech.setLanguage(
                                        Locale.US
                                );

                            }

                        }
                );


        speechRecognizer =
                SpeechRecognizer
                        .createSpeechRecognizer(
                                this
                        );


        speechRecognizer
                .setRecognitionListener(
                        new android.speech
                                .RecognitionListener() {

                    @Override
                    public void onReadyForSpeech(
                            Bundle params
                    ) {

                        statusText.setText(
                                "🎤 Sun raha hoon..."
                        );

                    }


                    @Override
                    public void onBeginningOfSpeech() {
                    }


                    @Override
                    public void onRmsChanged(
                            float rmsdB
                    ) {
                    }


                    @Override
                    public void onBufferReceived(
                            byte[] buffer
                    ) {
                    }


                    @Override
                    public void onEndOfSpeech() {

                        statusText.setText(
                                "Processing..."
                        );

                    }


                    @Override
                    public void onError(
                            int error
                    ) {

                        statusText.setText(
                                "Dobara try karein"
                        );

                    }


                    @Override
                    public void onResults(
                            Bundle results
                    ) {

                        ArrayList<String> matches =
                                results.getStringArrayList(
                                        SpeechRecognizer
                                                .RESULTS_RECOGNITION
                                );


                        if (
                                matches != null
                                &&
                                !matches.isEmpty()
                        ) {

                            String command =
                                    matches.get(0);


                            resultText.setText(
                                    "Tum: "
                                    + command
                            );


                            handleCommand(
                                    command.toLowerCase()
                            );

                        }

                    }


                    @Override
                    public void onPartialResults(
                            Bundle partialResults
                    ) {
                    }


                    @Override
                    public void onEvent(
                            int eventType,
                            Bundle params
                    ) {
                    }

                }
        );


        if (
                checkSelfPermission(
                        Manifest.permission.RECORD_AUDIO
                )
                != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.RECORD_AUDIO
                    },
                    REQUEST_AUDIO
            );

        }

    }


    private void startListening() {

        Intent intent =
                new Intent(
                        RecognizerIntent
                                .ACTION_RECOGNIZE_SPEECH
                );


        intent.putExtra(
                RecognizerIntent
                        .EXTRA_LANGUAGE_MODEL,
                RecognizerIntent
                        .LANGUAGE_MODEL_FREE_FORM
        );


        intent.putExtra(
                RecognizerIntent
                        .EXTRA_LANGUAGE,
                "en-US"
        );


        intent.putExtra(
                RecognizerIntent
                        .EXTRA_PROMPT,
                "JARVIS sun raha hai..."
        );


        startActivityForResult(
                intent,
                101
        );

    }


    private void handleCommand(
            String command
    ) {


        if (
                command.contains(
                        "whatsapp"
                )
        ) {

            openApp(
                    "com.whatsapp",
                    "WhatsApp"
            );

            return;

        }


        if (
                command.contains(
                        "telegram"
                )
        ) {

            openApp(
                    "org.telegram.messenger",
                    "Telegram"
            );

            return;

        }


        if (
                command.contains(
                        "facebook"
                )
        ) {

            openApp(
                    "com.facebook.katana",
                    "Facebook"
            );

            return;

        }


        if (
                command.contains(
                        "chrome"
                )
        ) {

            openApp(
                    "com.android.chrome",
                    "Chrome"
            );

            return;

        }


        if (
                command.contains(
                        "youtube"
                )
        ) {

            openApp(
                    "com.google.android.youtube",
                    "YouTube"
            );

            return;

        }


        if (
                command.contains(
                        "camera"
                )
        ) {

            Intent camera =
                    new Intent(
                            "android.media.action.IMAGE_CAPTURE"
                    );


            startActivity(
                    camera
            );


            speak(
                    "Camera open kar diya hai."
            );


            return;

        }


        if (
                command.contains(
                        "time"
                )
                ||
                command.contains(
                        "waqt"
                )
        ) {

            String time =
                    new java.text
                            .SimpleDateFormat(
                                    "hh:mm a",
                                    Locale.getDefault()
                            )
                            .format(
                                    new java.util.Date()
                            );


            speak(
                    "Abhi time hai "
                    + time
            );


            return;

        }


        speak(
                "Maine aapki command suni: "
                + command
        );

    }


    private void openApp(
            String packageName,
            String appName
    ) {

        try {

            Intent intent =
                    getPackageManager()
                            .getLaunchIntentForPackage(
                                    packageName
                            );


            if (
                    intent != null
            ) {

                startActivity(
                        intent
                );


                speak(
                        appName
                        + " open kar diya hai."
                );

            } else {

                speak(
                        appName
                        + " phone mein nahi mil raha."
                );

            }

        } catch (
                Exception e
        ) {

            speak(
                    appName
                    + " open nahi ho saka."
            );

        }

    }


    private void speak(
            String text
    ) {

        if (
                textToSpeech != null
        ) {

            textToSpeech.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "JARVIS"
            );

        }

    }


    @Override
    protected void onDestroy() {

        if (
                speechRecognizer != null
        ) {

            speechRecognizer.destroy();

        }


        if (
                textToSpeech != null
        ) {

            textToSpeech.stop();

            textToSpeech.shutdown();

        }


        super.onDestroy();

    }

}

