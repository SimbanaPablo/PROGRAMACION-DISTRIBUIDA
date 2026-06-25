```
packer init xx.pkr.hcl
packer build xx.pkr.hcl
```
# Editar la red
```
nano /etc/network/interfaces
==> cambiar ip y gateway
```
# Reiniciar la red
```
rc-service networking restart
```
## Configuracion Nomad
```
nano /etc/nomad.d/nomad.hcl
==> Cambiar la IP del servidor
```
## Reiniciar nomad
```
/etc/init.d/nomad restart
```