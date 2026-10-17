package lab8.s15_doubledispatch;

/**
 * Slide 15 — double dispatch, pas cu pas, cu urme afișate.
 * Ideea cheie: în interiorul Song.accept, „this" are tipul STATIC Song,
 * așa că overloading-ul v.visit(this) alege visit(Song) încă de la compilare.
 */
public class Demo11DoubleDispatch {

    interface Media { void accept(MediaVisitor v); }

    static class Song implements Media {
        public void accept(MediaVisitor v) {
            System.out.println("   dispatch 1: Song.accept (ales la rulare, după obiect)");
            v.visit(this);                       // this : Song
        }
    }

    static class Podcast implements Media {
        public void accept(MediaVisitor v) {
            System.out.println("   dispatch 1: Podcast.accept");
            v.visit(this);                       // this : Podcast
        }
    }

    interface MediaVisitor {
        void visit(Song s);
        void visit(Podcast p);
    }

    static class PlayVisitor implements MediaVisitor {
        public void visit(Song s)    { System.out.println("   dispatch 2: PlayVisitor.visit(Song) -> redau melodia"); }
        public void visit(Podcast p) { System.out.println("   dispatch 2: PlayVisitor.visit(Podcast) -> redau episodul"); }
    }

    static class DownloadVisitor implements MediaVisitor {
        public void visit(Song s)    { System.out.println("   dispatch 2: DownloadVisitor.visit(Song) -> descarc mp3"); }
        public void visit(Podcast p) { System.out.println("   dispatch 2: DownloadVisitor.visit(Podcast) -> descarc tot sezonul"); }
    }

    public static void main(String[] args) {
        Media[] items = { new Song(), new Podcast() };
        MediaVisitor[] visitors = { new PlayVisitor(), new DownloadVisitor() };

        for (MediaVisitor v : visitors) {
            for (Media m : items) {
                System.out.println("m.accept(" + v.getClass().getSimpleName() + ")");
                m.accept(v);
            }
        }
        System.out.println("\n2 tipuri de media x 2 vizitatori = 4 comportamente, fără niciun instanceof.");
    }
}
