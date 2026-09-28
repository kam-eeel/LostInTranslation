package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LanguageCodeConverter lcc = new LanguageCodeConverter();
            JSONTranslator translator = new JSONTranslator();

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languagePicker = new JComboBox<>();
            for (String languageCode : translator.getLanguageCodes()) {
                languagePicker.addItem(lcc.fromLanguageCode(languageCode));
            }
            languagePanel.add(languagePicker);

            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(new GridLayout(0, 3));
            resultPanel.add(new JLabel("Translation:"), 0);
            JLabel resultText = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultText);

            JPanel countryPanel = new JPanel();
            String[] countries = new String[translator.getCountryCodes().size()];
            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                countries[i++] = countryCode;
            }
            JList<String> countryPicker = new JList<>(countries);
            countryPanel.add(countryPicker);

            languagePicker.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (languagePicker.getSelectedItem() == null) { return; }

                    String language = languagePicker.getSelectedItem().toString();

                    int countryIndex = countryPicker.getSelectedIndex();

                    if (countryIndex == -1) { return; }

                    String result = translator.translate(
                            countryPicker.getModel().getElementAt(countryIndex),
                            lcc.fromLanguage(language)
                    );

                    if (result == null) {
                        result = "no translation found!";
                    }

                    resultText.setText(result);
                }
            });

            countryPicker.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int countryIndex = countryPicker.getSelectedIndex();

                    if (countryIndex == -1) {
                        return;
                    }

                    if (languagePicker.getSelectedItem() == null) {
                        return;
                    }

                    String language = languagePicker.getSelectedItem().toString();

                    String result = translator.translate(
                            countryPicker.getModel().getElementAt(countryIndex),
                            lcc.fromLanguage(language)
                    );

                    if (result == null) {
                        result = "no translation found!";
                    }

                    resultText.setText(result);
                }
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        });
    }
}
