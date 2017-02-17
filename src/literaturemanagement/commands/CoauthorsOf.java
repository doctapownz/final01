package literaturemanagement.commands;

import edu.kit.informatik.Terminal;
import literaturemanagement.LiteratureManager;
import literaturemanagement.entities.Author;
import literaturemanagement.lists.ArticleList;
import literaturemanagement.lists.AuthorList;

public class CoauthorsOf extends Command {
    protected CoauthorsOf() {
        super(RegexConstant.COAUTHORS_OF);
    }

    @Override
    public String cutString(String input) {
        return cutStringHelper(input, 2);
    }

    @Override
    public void execute(LiteratureManager literatureManager, String input) {
        String cutString = cutString(input);

        String[] splitCutString = cutString.split(" ");

        String preName = splitCutString[0];
        String lastName = splitCutString[1];

        Author ourAuthor = new Author(preName, lastName);

        if (!literatureManager.getAuthorList().contains(ourAuthor)) {
            Terminal.printError("Author not found");
            return;
        }

        ArticleList articleByAuthor = literatureManager.getArticleList().getByAuthor(ourAuthor);

        AuthorList coAuthors = new AuthorList();

        for (int i = 0; i < articleByAuthor.getLength(); i++) {
            for (int j = 0; j < articleByAuthor.getAtIndex(i).getAuthorList().getLength(); j++) {
                if (!coAuthors.contains(articleByAuthor.getAtIndex(i).getAuthorList().getAtIndex(j))) {
                    coAuthors.addAuthor(articleByAuthor.getAtIndex(i).getAuthorList().getAtIndex(j));
                }

            }

        }

        for (int i = 0; i < coAuthors.getLength(); i++) {
            Terminal.printLine(coAuthors.getAtIndex(i).toString());

        }

    }
}
