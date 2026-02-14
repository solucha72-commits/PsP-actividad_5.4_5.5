package agenda.controladores;

import agenda.entidades.Contacto;
import agenda.servicios.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // IMPORTANTE
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    @Autowired
    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping
    public List<Contacto> obtenerTodos() {
        return contactoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Contacto obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id);
    }

    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto) {
        return contactoService.guardar(contacto);
    }

    @PutMapping("/{id}")
    public Contacto actualizar(@PathVariable Long id, @RequestBody Contacto contacto) {
        return contactoService.actualizar(id, contacto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
    }
}
