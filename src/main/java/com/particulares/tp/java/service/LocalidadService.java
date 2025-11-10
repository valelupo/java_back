package com.particulares.tp.java.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.repository.PersonaRepository;
import com.particulares.tp.java.repository.ProvinciaRepository;


@Service
public class LocalidadService {
    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public Localidad crearLocalidad(String nombre, Integer codPostal, Integer idProvincia)throws Exception {
        validar(nombre,codPostal,idProvincia);

        Provincia provincia = provinciaRepository.findById(idProvincia).get();

        Localidad existente = localidadRepository.findByNombreOrCodigoPostal(nombre, codPostal);
        if (existente != null) {
            throw new Exception("Ya existe una localidad con ese nombre o código postal.");
        }

        Localidad localidad = new Localidad();
        localidad.setNombre(nombre);
        localidad.setCodPostal(codPostal);
        localidad.setMiProvincia(provincia);

        return localidadRepository.save(localidad);
    }

    @Transactional(readOnly = true)
    public List<Localidad> listarLocalidades() {
        return localidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Localidad> listarLocalidadesPorProvincia(String provincia) {
        return localidadRepository.buscarPorProvinciaNombre(provincia);
    }

    @Transactional(readOnly = true)
    public List<Localidad> buscarPorProvincia(int idProv){
        return localidadRepository.buscarPorProvinciaId(idProv);
    }

    @Transactional
    public void eliminarLocalidad(int id) throws Exception{
        Optional<Localidad> localidadOpt = localidadRepository.findById(id);

        if (localidadOpt.isPresent()) {

            List <Localidad> localidadesPersonas = personaRepository.buscarLocalidades();
            Localidad loc = localidadOpt.get();

            if (!localidadesPersonas.isEmpty()){
                for (Localidad l : localidadesPersonas) {
                    if(l.getId()==loc.getId()){
                        throw new Exception("Existen profesores o alumnos que pertenecen a esta localidad");}
                }
            }
            localidadRepository.delete(loc);
                        
        } else {
            throw new Exception("La localidad con el id especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Localidad  getOne(int id){
        return localidadRepository.getReferenceById (id);
    }

    private void validar(String nombre, Integer codPostal, Integer idProvincia)
            throws Exception {       
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }
        if(idProvincia == null) {
            throw new Exception("El idProvincia no puede ser nulo o estar vacio");
        }
        if (codPostal == null) {
            throw new Exception("El codPostal no puede ser nulo o estar vacio");
        }
    }
    
}

