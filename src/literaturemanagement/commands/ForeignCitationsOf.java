package literaturemanagement.commands;

import edu.kit.informatik.Terminal;
import literaturemanagement.LiteratureManager;
import literaturemanagement.entities.Author;
import literaturemanagement.lists.ArticleList;
import literaturemanagement.lists.AuthorList;

public class ForeignCitationsOf extends Command {
    protected ForeignCitationsOf() {
        super(RegexConstant.FOREIGN_CITATIONS_OF);
    }

    @Override
    public String cutString(String input) {
        return cutStringHelper(input,3);
    }

    @Override
    public void execute(LiteratureManager literatureManager, String input) {
        String cutString = cutString(input);

        String[] splitCutString = cutString.split(" ");

        String firstName = splitCutString[0];
        String lastName = splitCutString[1];

        Author ourAuthor = new Author(firstName, lastName);

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
        // added the author himself
        coAuthors.addAuthor(ourAuthor);

        ArticleList notWrittenByCoAuthors = literatureManager.getArticleList().notWrittenBy(coAuthors);

        ArticleList hasReferenceOnArticlesByAuthor = notWrittenByCoAuthors.hasReferenceOn(articleByAuthor);

        for (int i = 0; i < hasReferenceOnArticlesByAuthor.getLength(); i++) {
            Terminal.printLine(hasReferenceOnArticlesByAuthor.getAtIndex(i).getIdentifier());

        }


    }
}
