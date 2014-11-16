package se.dse.pulsar.devtools.shell;


import se.dse.pulsar.devtools.shell.api.DevCommandService;

public class DevCommandServiceImpl implements DevCommandService {

    @Override
    public void alfa(String i_param) {

        System.out.println("alfa:"+i_param);

    }

    @Override
    public void beta(String... i_params) {
        for (String s : i_params) {
            System.out.println("-- "+s);
        }
    }


}
