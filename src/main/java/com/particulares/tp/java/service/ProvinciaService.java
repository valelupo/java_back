package com.particulares.tp.java.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.repository.PersonaRepository;
import com.particulares.tp.java.repository.ProvinciaRepository;


@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public void crearProvincia(String nombre) throws Exception {
        validar(nombre);

        Provincia existente = provinciaRepository.findByNombre(nombre);
        if (existente != null) {
            throw new Exception("Ya existe una provincia con este nombre");
        }

        Provincia provincia = new Provincia();
        provincia.setNombre(nombre);
        provinciaRepository.save(provincia);
    }


    @Transactional(readOnly = true)
    public List<Provincia> listarProvincias() {
        return provinciaRepository.findAll();
    }

    @Transactional
    public void eliminarProvincia(int id) throws Exception{
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(id);

        if (provinciaOpt.isPresent()) {

            List <Provincia> provinciasPersonas = personaRepository.buscarProvincias();
            Provincia prov = provinciaOpt.get();

            if (!provinciasPersonas.isEmpty()){
                for (Provincia p : provinciasPersonas) {
                    if(p.getId()==prov.getId()){
                        throw new Exception("Existen profesores o alumnos que pertenecen a esta provincia");}
                }
            }
            provinciaRepository.delete(prov);
            
            
        } else {
            throw new Exception("La provincia con el id especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Provincia getOne(int id){
        return provinciaRepository.getReferenceById(id);
    }


    private void validar(String nombre) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
    }
    
}