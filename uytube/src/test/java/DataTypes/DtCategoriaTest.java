package DataTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtCategoriaTest {

    DtCategoria test = new DtCategoria("cat");

    @Test
    void getSerialVersionUID() {
        assertEquals(1L,test.getSerialVersionUID());
    }

    @Test
    void getnombreCategoria() {
        assertEquals("cat",test.getnombreCategoria());
    }
}