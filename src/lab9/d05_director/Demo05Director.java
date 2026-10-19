package lab9.d05_director;

/**
 * Slide 5 — Builder-ul GoF: același Director, aceiași pași, reprezentări diferite.
 * Construim un CV o dată ca text Markdown, o dată ca HTML.
 */
public class Demo05Director {

    // Builder-ul abstract: pașii
    interface CvBuilder {
        void name(String name);
        void section(String title);
        void item(String text);
    }

    // Builder concret 1 → produs: String Markdown
    static class MarkdownCvBuilder implements CvBuilder {
        private final StringBuilder out = new StringBuilder();
        public void name(String name) { out.append("# ").append(name).append('\n'); }
        public void section(String title) { out.append("\n## ").append(title).append('\n'); }
        public void item(String text) { out.append("- ").append(text).append('\n'); }
        String getMarkdown() { return out.toString(); }
    }

    // Builder concret 2 → produs: String HTML
    static class HtmlCvBuilder implements CvBuilder {
        private final StringBuilder out = new StringBuilder();
        private boolean listOpen = false;
        public void name(String name) { out.append("<h1>").append(name).append("</h1>\n"); }
        public void section(String title) {
            closeList();
            out.append("<h2>").append(title).append("</h2>\n<ul>\n");
            listOpen = true;
        }
        public void item(String text) { out.append("  <li>").append(text).append("</li>\n"); }
        private void closeList() { if (listOpen) { out.append("</ul>\n"); listOpen = false; } }
        String getHtml() { closeList(); return out.toString(); }
    }

    // Builder concret 3 → produs complet diferit: un număr
    static class WordCountBuilder implements CvBuilder {
        private int words = 0;
        private int count(String s) { return s.isBlank() ? 0 : s.trim().split("\\s+").length; }
        public void name(String name) { words += count(name); }
        public void section(String title) { words += count(title); }
        public void item(String text) { words += count(text); }
        int getWords() { return words; }
    }

    // Directorul: știe ORDINEA pașilor, nu știe ce se construiește
    static class CvDirector {
        void makeStudentCv(CvBuilder b) {
            b.name("Ana Popescu");
            b.section("Educație");
            b.item("Facultatea de Automatică și Calculatoare, anul II");
            b.section("Proiecte");
            b.item("Joc de șah în Java");
            b.item("Bot de Discord");
        }
    }

    public static void main(String[] args) {
        CvDirector director = new CvDirector();

        MarkdownCvBuilder md = new MarkdownCvBuilder();
        director.makeStudentCv(md);
        System.out.println(md.getMarkdown());

        HtmlCvBuilder html = new HtmlCvBuilder();
        director.makeStudentCv(html);
        System.out.println(html.getHtml());

        WordCountBuilder wc = new WordCountBuilder();
        director.makeStudentCv(wc);
        System.out.println("cuvinte: " + wc.getWords());

        // DEMO EROARE: decomentează → cannot find symbol: getHtml() — interfața nu știe tipul produsului
        // CvBuilder any = new HtmlCvBuilder(); director.makeStudentCv(any); System.out.println(any.getHtml());
    }
}
