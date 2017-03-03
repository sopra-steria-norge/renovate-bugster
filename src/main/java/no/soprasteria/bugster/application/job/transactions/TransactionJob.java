package no.soprasteria.bugster.application.job.transactions;

import no.soprasteria.bugster.business.transaction.domain.Transaction;
import no.soprasteria.bugster.business.transaction.service.TransactionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class TransactionJob implements Job {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransactionJob.class);

    private TransactionService service = new TransactionService();

    public TransactionJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("Starter utbetaling");
            List<Transaction> allUnhandled = service.findAllUnhandled();
            log.info("Fant {} utbetalinger", allUnhandled.size());
            for (Transaction transaction : allUnhandled) {
                if(transaction.isRevoked()) {
                    service.undo(transaction);
                } else {
                    service.expedite(transaction);
                }
            }
            log.info("Ferdig med utbetalinger");
        } catch (Exception e) {
            log.error("Feilet under utbetaling, prøver igjen om litt", e);
        }
    }
}
