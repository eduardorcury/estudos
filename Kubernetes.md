# Kubernetes (k8s)

> Orquestrador de contâineres capaz

## Definições

1. Cluster: conjunto de máquinas virtuais (chamadas de nós) que executam aplicações em containers.
> A set of worker machines, called nodes, that run containerized applications. Every cluster has at least one worker node - [fonte](https://kubernetes.io/docs/reference/glossary/?all=true#term-cluster)
2. Pods: unidade que encapsula um container. Em kubernetes, todos os containers ficam dentro de pods.
> The smallest and simplest Kubernetes object. A Pod represents a set of running containers on your cluster - [fonte](https://kubernetes.io/docs/reference/glossary/?all=true#term-pod)
3. Master: máquinas virtuais responsáveis por gerenciar o cluster.
4. Node: máquinas virtuais responsáveis por executar as aplicações.
5. API: usada como meio de comunicação para executar ações nos nós.
6. kubctl: ferramenta de linha de comando que permite fazer requests à API.

### Pods

- Cada pod tem o seu endereço IP, dessa forma os containers contidos nele podem ser mapeados para diferentes portes nesse endereço. Ou seja, containers do mesmo Pod não podem ter portas iguais.
- Pods são descartáveis. Se todos os containers dentro de um Pod morrerem, ele será destruíd e o kubernetes se encarregará de criar um novo pod, **não necessariamente com o mesmo IP**.
- Pulo do gato: como os containers dentro de um pod estão na mesma rede (com o mesmo endereço IP), eles podem se comunicar via localhost!

1. Criando Pod por linha de comando:

```bash
kubectl run <nome-do-pod> --image=<nome da imagem>
```
```bash
kubectl run nginx-pod --image=nginx:latest
kubectl get pods
kubectl describe pod nginx-pod
```

2. Criando Pod por arquivo de configuração:

```yml
apiVersion: v1
kind: Pod
metadata:
	name: primeiro-pod-declarativo
spec:
	containers:
		- name: nginx-container
		  image: nginx:latest
```
```bash
kubectl apply -f .\primeiro-pod.yml
```

O IP do Pod é para acesso dentro do cluster. Para acesso dos containers fora do cluster (em um navegador, por exemplo), precisamos **expor** o Pod.

