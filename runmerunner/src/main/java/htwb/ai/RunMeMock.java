package htwb.ai;

public class RunMeMock {
    @RunMe
    public RunMeMock(){
    }

    @RunMe
    public void voidMethod(){

    }

    @RunMe
    public String stringMethod(){
        return null;
    }

    public int intWithoutRunMe(){
        return 0;
    }

    @RunMe
    public int intWithParameter(int i ){
        return 0;
    }

    @RunMe
    private int intWithParameterPrivate(int i ){
        return 0;
    }

    @RunMe
    private void privateRunMe(){
    }


}
