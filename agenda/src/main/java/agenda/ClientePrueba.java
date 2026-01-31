package agenda;


import agenda.entidades.Contacto;
import agenda.servicios.ContactoService;
import agenda.servicios.ContactoServiceImpl;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ClientePrueba {

    private static  final String BASE_URL = "http://localhost:8080/contactos";

    public static void main(String[] args) {

        Contacto nuevocontacto=new Contacto("Santiago","964123456");


        ClientePrueba cliente =new ClientePrueba();
        cliente.realizarpruebas(nuevocontacto);

    }

    public void realizarpruebas(Contacto nuevocontacto) {
        //agregamos el contacto
        nuevocontacto = agregarContacto(nuevocontacto);
        System.out.println("Contacto agregado: " + nuevocontacto);

        nuevocontacto.setTelefono("666777888");
        nuevocontacto = modificarContacto(nuevocontacto.getId(), nuevocontacto);
        System.out.println("Contacto modificado: " + nuevocontacto);

        //listado de ontactos
        listarContactos();

        //obtener id Contaacto
        obtener_contacto_porID(nuevocontacto.getId());

        // Elimina un contacto por ID
        eliminarContacto(nuevocontacto.getId());
        // Obtiene todos los contactos después de la eliminación
        listarContactos();
    }

    private void eliminarContacto(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(BASE_URL + "/" + id);
        System.out.println("Contacto eliminado con ID: " + id);
    }

    private void obtener_contacto_porID(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        Contacto contacto =
                restTemplate.getForObject(BASE_URL + "/" + id, Contacto.class);
        System.out.println("Contacto obtenido por ID: " + contacto);
    }

    private void listarContactos() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contacto[]> response =
                restTemplate.getForEntity(BASE_URL, Contacto[].class);

        Contacto[] contactos = response.getBody();
        System.out.println("Lista de contactos:");
        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }
    }

    private Contacto agregarContacto(Contacto contacto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contacto> response =
                restTemplate.postForEntity(BASE_URL, contacto, Contacto.class);
        return response.getBody();
    }
    private Contacto modificarContacto(Long id, Contacto contacto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Contacto> requestEntity =
                new HttpEntity<>(contacto, headers);

        ResponseEntity<Contacto> response =
                restTemplate.exchange(
                        BASE_URL + "/" + id,
                        HttpMethod.PUT,
                        requestEntity,
                        Contacto.class
                );

        return response.getBody();
    }

}

