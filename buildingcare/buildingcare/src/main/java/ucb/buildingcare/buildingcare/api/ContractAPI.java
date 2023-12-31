package ucb.buildingcare.buildingcare.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ucb.buildingcare.buildingcare.bl.ContractsBl;
import ucb.buildingcare.buildingcare.dto.BuildingcareResponse;
import ucb.buildingcare.buildingcare.dto.ContractRequest;
import ucb.buildingcare.buildingcare.util.BuildingcareException;

@RestController
@RequestMapping(path = "/api/v1/contract")
public class ContractAPI {
    //Esta API se encarga de la logica sobre los contratos
    //Requiere de los servicios:
    //contractBl

    Logger LOGGER = LoggerFactory.getLogger(ContractAPI.class);

    @Autowired
    private ContractsBl contractBl;

    public ContractAPI(ContractsBl contractBl) {
        this.contractBl = contractBl;
    }

    @GetMapping
    public BuildingcareResponse ListAllContracts(@RequestParam(required = false) Integer type) {
        LOGGER.info("ListAllContracts");
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        
        try {
            buildingcareResponse = contractBl.getContracts(type);
            buildingcareResponse.setResponseCode("CONT-0000");
            LOGGER.info("se obtuvieron todos los contratos: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6000");
            LOGGER.info("no se pudieron obtener todos los contratos: "+ buildingcareResponse.toString());
        }
        
        LOGGER.info("{}", buildingcareResponse);
        return buildingcareResponse;
    }

    @GetMapping(path = "/type")
    public BuildingcareResponse getTypeContract() {
        LOGGER.info("getTypeContract");
        BuildingcareResponse buildingcareResponse;
        try {
            buildingcareResponse = contractBl.getTypeContract();
            buildingcareResponse.setResponseCode("POST-0004");
        } catch (BuildingcareException e) {
            buildingcareResponse = new BuildingcareResponse();
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("POST-6004");
        }
        return buildingcareResponse;
    }

    @GetMapping(path = "/{id}")
    public BuildingcareResponse getContractById(@PathVariable Integer id) {
        LOGGER.info("getContractById: id: {}", id);
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        try {
            buildingcareResponse.setData(contractBl.getContractById(id));
            buildingcareResponse.setResponseCode("CONT-0000");
            LOGGER.info("se obtuvo el contrato: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6000");
            LOGGER.info("no se pudo obtener el contrato: "+ buildingcareResponse.toString());
        }
        LOGGER.info("{}", buildingcareResponse);
        return buildingcareResponse;
    }

    @GetMapping(path = "/type/{id}")
    public BuildingcareResponse getTypeContractById(@PathVariable Integer id) {
        LOGGER.info("getContractById: id: {}", id);
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        try {
            buildingcareResponse.setData(contractBl.getTypeContractById(id));
            buildingcareResponse.setResponseCode("CONT-0000");
            LOGGER.info("se obtuvo el contrato: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6000");
            LOGGER.info("no se pudo obtener el contrato: "+ buildingcareResponse.toString());
        }
        LOGGER.info("{}", buildingcareResponse);
        return buildingcareResponse;
    }

    @CrossOrigin (origins = "http://localhost:8081")
    @PostMapping()
    public BuildingcareResponse createContract(@RequestBody ContractRequest contractRequest, @RequestHeader Integer token) {
        LOGGER.info("Creando contrato");
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        try {
            buildingcareResponse.setData(contractBl.createContract(contractRequest, token));
            buildingcareResponse.setResponseCode("CONT-0001");
            LOGGER.info("se creó el contrato: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6001");
            LOGGER.info("no se pudo crear el contrato: "+ buildingcareResponse.toString());
        }
        return buildingcareResponse;
    }
    @PutMapping(path = "/{id}")
    public BuildingcareResponse updateContract(@PathVariable Integer id, @RequestBody ContractRequest contractRequest, @RequestHeader Integer token) {
        LOGGER.info("Actualizando contrato");
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        try {
            buildingcareResponse.setData(contractBl.updateContract(id, contractRequest, token));
            buildingcareResponse.setResponseCode("CONT-0002");
            LOGGER.info("se actualizó el contrato: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6002");
            LOGGER.info("no se pudo actualizar el contrato: "+ buildingcareResponse.toString());
        }
        return buildingcareResponse;
    }
    @DeleteMapping(path = "/{id}")
    public BuildingcareResponse deleteContract(@PathVariable Integer id) {
        LOGGER.info("Eliminando contrato");
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse();
        try {
            buildingcareResponse.setData(contractBl.deleteContract(id));
            buildingcareResponse.setResponseCode("CONT-0003");
            LOGGER.info("se eliminó el contrato: "+ buildingcareResponse.toString());
        } catch (Exception e) {
            buildingcareResponse.setErrorMessage(e.getMessage());
            buildingcareResponse.setResponseCode("CONT-6003");
            LOGGER.info("no se pudo eliminar el contrato: "+ buildingcareResponse.toString());
        }
        return buildingcareResponse;
    }
}
