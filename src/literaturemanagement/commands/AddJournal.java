package literaturemanagement.commands;

import edu.kit.informatik.Terminal;
import literaturemanagement.LiteratureManager;
import literaturemanagement.entities.Journal;

public class AddJournal extends Command{
    protected AddJournal() {
        super(RegexConstant.ADD_JOURNAL);
    }

    @Override
    public String cutString(String input) {
        return cutStringHelper(input, 2);
    }

    @Override
    public void execute(LiteratureManager literatureManager, String input) {
        String cutInput = cutString(input);
        String[] splitCuttedInput = cutInput.split(",");

        Journal journalToAdd = new Journal(splitCuttedInput[0], splitCuttedInput[1]);

        if (literatureManager.getJournalList().contains(journalToAdd)) {
            Terminal.printError("Journal is already published");
            return;
        }

        literatureManager.getJournalList().addJournal(journalToAdd);

        Terminal.printLine("Ok");

    }
}
