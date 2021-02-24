package ejercicio4Test;

import ejercicio3.Asfi;
import ejercicio3.Credito;
import ejercicio4.BDUtil;
import ejercicio4.Cajero;
import ejercicio4.ClientDB;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@RunWith(value= Parameterized.class)
public class CajeroTest {

    private int ci;
    private int amount;
    private int saldo;
    private String expectedResult;
    private boolean connectionExpectedMock;
    private boolean updateExpectedMock;

    public CajeroTest(int ci, int amount, int saldo, String expectedResult,boolean connectionExpectedMock,boolean updateExpectedMock){
        this.ci=ci;
        this.amount=amount;
        this.saldo=saldo;
        this.expectedResult=expectedResult;
        this.connectionExpectedMock=connectionExpectedMock;
        this.updateExpectedMock=updateExpectedMock;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> getData(){
        List<Object[]> objects= new ArrayList<>();
                               // ci  amount saldo , expectedResult         , connection, updated
        objects.add(new Object[]{989898,100,200,"Conexion a BD no fue satisfactoria",false,false});
        objects.add(new Object[]{989898,100,200,"Actualizacion Incorrecta, Intente Nuevamente",true,false});

        objects.add(new Object[]{989898,0,200,"Amount No Valido",true,true});

        objects.add(new Object[]{989898,100,50,"Usted no tiene suficiente saldo",true,true});
        objects.add(new Object[]{989898,1,200,"Usted esta sacando la cantidad de 1 y tiene en saldo 199",true,true});
        objects.add(new Object[]{989898,2,200,"Usted esta sacando la cantidad de 2 y tiene en saldo 198",true,true});
        objects.add(new Object[]{989898,99,200,"Usted esta sacando la cantidad de 99 y tiene en saldo 101",true,true});
        objects.add(new Object[]{989898,100,200,"Usted esta sacando la cantidad de 100 y tiene en saldo 100",true,true});
        objects.add(new Object[]{989898,101,200,"Usted esta sacando la cantidad de 101 y tiene en saldo 99",true,true});
        objects.add(new Object[]{989898,199,200,"Usted esta sacando la cantidad de 199 y tiene en saldo 1",true,true});
        objects.add(new Object[]{989898,200,200,"Usted esta sacando la cantidad de 200 y tiene en saldo 0",true,true});
        objects.add(new Object[]{989898,201,200,"Usted no tiene suficiente saldo",true,true});


        return objects;
    }
    // PASO 2
    BDUtil bdUtilMock= Mockito.mock(BDUtil.class);
    ClientDB clientDBMock= Mockito.mock(ClientDB.class);
    @Test
    public void verify_calculate_credit(){
        // Paso 3
        Mockito.when(clientDBMock.isConnectionSuccessfully("mysql")).thenReturn(this.connectionExpectedMock);
        Mockito.when(bdUtilMock.updateSaldo(this.ci,this.saldo-this.amount)).thenReturn(this.updateExpectedMock);

       //  Paso 4
        Cajero cajero= new Cajero(this.saldo,bdUtilMock,clientDBMock);
        String actualResult= cajero.getCash(this.ci,this.amount);
        Assert.assertEquals("ERROR! ",this.expectedResult,actualResult);
    }

}
