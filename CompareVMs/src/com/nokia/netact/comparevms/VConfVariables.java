package com.nokia.netact.comparevms;

import java.util.List;

public class VConfVariables {

    public VConfVariables() {
    }

    public VConfVariables(List<VM> vm) {
        super();
        this.vm = vm;
    }

    private List<VM> vm;

    public List<VM> getVm() {
        return vm;
    }

    public void setVm(List<VM> vm) {
        this.vm = vm;
    }

}

class VM {
    private String vm_name;
    private String res_pool_name;
    private String datastore_name;
    private String drs_group_name;
    private List<Disk> disk;
    private String type;
    private String guest_os;
    private String memoryMB;
    private String memory_reservation;
    private List<NetworkInterface> network_interface;
    private List<SCSIcontroller> scsi_controller;
    private String unique_id;
    private String vcpu;

    public VM() {
    }

    public VM(String vm_name, String res_pool_name, String datastore_name, String drs_group_name, List<Disk> disk,
            String type, String guest_os, String memoryMB, String memory_reservation,
            List<NetworkInterface> network_interface, List<SCSIcontroller> scsi_controller, String unique_id,
            String vcpu) {
        super();
        this.vm_name = vm_name;
        this.res_pool_name = res_pool_name;
        this.datastore_name = datastore_name;
        this.drs_group_name = drs_group_name;
        this.disk = disk;
        this.type = type;
        this.guest_os = guest_os;
        this.memoryMB = memoryMB;
        this.memory_reservation = memory_reservation;
        this.network_interface = network_interface;
        this.scsi_controller = scsi_controller;
        this.unique_id = unique_id;
        this.vcpu = vcpu;
    }

    public String getVm_name() {
        return vm_name;
    }

    public void setVm_name(String vm_name) {
        this.vm_name = vm_name;
    }

    public String getRes_pool_name() {
        return res_pool_name;
    }

    public void setRes_pool_name(String res_pool_name) {
        this.res_pool_name = res_pool_name;
    }

    public String getDatastore_name() {
        return datastore_name;
    }

    public void setDatastore_name(String datastore_name) {
        this.datastore_name = datastore_name;
    }

    public String getDrs_group_name() {
        return drs_group_name;
    }

    public void setDrs_group_name(String drs_group_name) {
        this.drs_group_name = drs_group_name;
    }

    public List<Disk> getDisk() {
        return disk;
    }

    public void setDisk(List<Disk> disk) {
        this.disk = disk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGuest_os() {
        return guest_os;
    }

    public void setGuest_os(String guest_os) {
        this.guest_os = guest_os;
    }

    public String getMemoryMB() {
        return memoryMB;
    }

    public void setMemoryMB(String memoryMB) {
        this.memoryMB = memoryMB;
    }

    public String getMemory_reservation() {
        return memory_reservation;
    }

    public void setMemory_reservation(String memory_reservation) {
        this.memory_reservation = memory_reservation;
    }

    public List<NetworkInterface> getNetwork_interface() {
        return network_interface;
    }

    public void setNetwork_interface(List<NetworkInterface> network_interface) {
        this.network_interface = network_interface;
    }

    public List<SCSIcontroller> getScsi_controller() {
        return scsi_controller;
    }

    public void setScsi_controller(List<SCSIcontroller> scsi_controller) {
        this.scsi_controller = scsi_controller;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getVcpu() {
        return vcpu;
    }

    public void setVcpu(String vcpu) {
        this.vcpu = vcpu;
    }
}

class Disk {
	private String disk_name;
	private String datastore_name;
	private String diskMode;
	private String scsi_controller_id;
	private String scsi_id;
	private String sizeMB;
	private String storage_format;

	public Disk() {}

	public Disk(String disk_name, String datastore_name, String diskMode, String scsi_controller_id, String scsi_id,
			String sizeMB, String storage_format) {
		super();
		this.disk_name = disk_name;
		this.datastore_name = datastore_name;
		this.diskMode = diskMode;
		this.scsi_controller_id = scsi_controller_id;
		this.scsi_id = scsi_id;
		this.sizeMB = sizeMB;
		this.storage_format = storage_format;
	}

	public String getDisk_name() {
		return disk_name;
	}

	public void setDisk_name(String disk_name) {
		this.disk_name = disk_name;
	}

	public String getDatastore_name() {
		return datastore_name;
	}

	public void setDatastore_name(String datastore_name) {
		this.datastore_name = datastore_name;
	}

	public String getDiskMode() {
		return diskMode;
	}

	public void setDiskMode(String diskMode) {
		this.diskMode = diskMode;
	}

	public String getScsi_controller_id() {
		return scsi_controller_id;
	}

	public void setScsi_controller_id(String scsi_controller_id) {
		this.scsi_controller_id = scsi_controller_id;
	}

	public String getScsi_id() {
		return scsi_id;
	}

	public void setScsi_id(String scsi_id) {
		this.scsi_id = scsi_id;
	}

	public String getSizeMB() {
		return sizeMB;
	}

	public void setSizeMB(String sizeMB) {
		this.sizeMB = sizeMB;
	}

	public String getStorage_format() {
		return storage_format;
	}

	public void setStorage_format(String storage_format) {
		this.storage_format = storage_format;
	}

}

class NetworkInterface {
	private String network_interface_name;
	private String vds_name;
	private String portgroup_name;
	private String network_adapter_type;

	public NetworkInterface() {}

	public NetworkInterface(String network_interface_name, String vds_name, String portgroup_name,
			String network_adapter_type) {
		super();
		this.network_interface_name = network_interface_name;
		this.vds_name = vds_name;
		this.portgroup_name = portgroup_name;
		this.network_adapter_type = network_adapter_type;
	}

	public String getNetwork_interface_name() {
		return network_interface_name;
	}

	public void setNetwork_interface_name(String network_interface_name) {
		this.network_interface_name = network_interface_name;
	}

	public String getVds_name() {
		return vds_name;
	}

	public void setVds_name(String vds_name) {
		this.vds_name = vds_name;
	}

	public String getPortgroup_name() {
		return portgroup_name;
	}

	public void setPortgroup_name(String portgroup_name) {
		this.portgroup_name = portgroup_name;
	}

	public String getNetwork_adapter_type() {
		return network_adapter_type;
	}

	public void setNetwork_adapter_type(String network_adapter_type) {
		this.network_adapter_type = network_adapter_type;
	}
}

class SCSIcontroller {
	private String id;
	private String scsi_controller_type;

	public SCSIcontroller() {}

	public SCSIcontroller(String id, String scsi_controller_type) {
		super();
		this.id = id;
		this.scsi_controller_type = scsi_controller_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScsi_controller_type() {
		return scsi_controller_type;
	}

	public void setScsi_controller_type(String scsi_controller_type) {
		this.scsi_controller_type = scsi_controller_type;
	}
}