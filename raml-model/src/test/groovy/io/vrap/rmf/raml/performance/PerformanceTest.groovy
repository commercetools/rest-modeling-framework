package io.vrap.rmf.raml.performance

import io.vrap.rmf.raml.model.RamlModelBuilder
import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import org.eclipse.emf.common.util.URI

import java.time.Duration
import java.time.temporal.ChronoUnit

class PerformanceTest {

    static void main(String... args) {
        URI file = URI.createFileURI(args[0])
        println("Start performance test by pressing RETURN")
        System.in.newReader().readLine();
        long startTime = System.currentTimeMillis();
        RamlModelResult<Api> result = new RamlModelBuilder().buildApi(file);
        long endTime = System.currentTimeMillis();
        if (result.validationResults.isEmpty()) {
            Duration d = Duration.ofMillis(endTime - startTime);
            println("Finished parsing in ${d.get(ChronoUnit.SECONDS)} secs")
        }
    }
}
