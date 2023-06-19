package br.com.sysmap;

import br.com.sysmap.driver.Api;
import br.com.sysmap.driver.Browser;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OpenWeatherTest {

    @BeforeAll
    static void inicio() {
        Browser.abrirChrome("https://openweathermap.org/");
    }

    @Test
    @Order(2)
    void validarSite() {
        boolean validate = Browser.elementoExiste(By.xpath("//div[@class='mobile-padding']//span[.='OpenWeather']"));
        assertTrue(validate);
        System.out.println("Validado que estamos no site do Open Weather");
    }

    @Test
    @Order(3)
    void ct1ConsultarCidade()  {
        System.out.println("Digitando nome da cidade Barueri");
        Browser.digitar(By.xpath("//div[@class='search-container']//input"), "Barueri");
        System.out.println("Clicando no Botão Search para consulta");
        Browser.aguardar(5);
        Browser.clicar(By.xpath("//div[@class='search']//button[text()='Search']"));
        Browser.aguardar(2);
        Browser.clicar(By.xpath("//ul[@class='search-dropdown-menu']"));
        Browser.aguardar(2);
        var searchCity = Browser.lerTexto(By.xpath("//div[@class='current-container mobile-padding']//h2"));
        System.out.println("Validando se o nome digitado na Step1 é igual ao resultado do nome da cidade exibido no site.");
        assertEquals("Barueri, BR", searchCity);
    }

    @Test
    @Order(4)
    void ct2ValidarTemperaturaCelsius()  {
        Browser.clicar(By.xpath("//div[@class='switch-container']//div[text()='Metric: °C, m/s']"));
        Browser.aguardar(2);
        String tempSite = Browser.lerTexto(By.xpath("//div[@class='current-temp']//span")).replace("°C", "");
        String valorApi = Api.currentWeatherUnits("Barueri", "metric");
        System.out.println("Validando temperatura em graus Celsius");
        assertEquals(Long.parseLong(tempSite), Math.round(Double.parseDouble(valorApi)));
    }

    @Test
    @Order(5)
    void ct3ValidarTemperaturaFahrenheit()  {
        Browser.clicar(By.xpath("//div[@class='switch-container']//div[text()='Imperial: °F, mph']"));
        Browser.aguardar(2);
        String tempSite = Browser.lerTexto(By.xpath("//div[@class='current-temp']//span")).replace("°F", "");
        String valorApi = Api.currentWeatherUnits("Barueri", "imperial");
        System.out.println("Validando temperatura em graus Fahrenheit");
        assertEquals(Long.parseLong(tempSite), Math.round(Double.parseDouble(valorApi)));
    }

    @AfterAll
    static void fim() {
        Browser.fecharChrome();
    }
}
