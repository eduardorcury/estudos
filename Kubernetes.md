# Kubernetes (k8s)

## Definições

1. Cluster: conjunto de máquinas virtuais (chamadas de nós) que executam aplicações em containers.
> A set of worker machines, called nodes, that run containerized applications. Every cluster has at least one worker node - [fonte](https://kubernetes.io/docs/reference/glossary/?all=true#term-cluster)
2. Pods: unidade que encapsula um container. Em kubernetes, todos os containers ficam dentro de pods.
> The smallest and simplest Kubernetes object. A Pod represents a set of running containers on your cluster - [fonte](https://kubernetes.io/docs/reference/glossary/?all=true#term-pod)
3. Master: máquinas virtuais responsáveis por gerenciar o cluster.
4. Node: máquinas virtuais responsáveis por executar as aplicações.
5. API: usada como meio de comunicação para executar ações nos nós.
6. kubctl: ferramenta de linha de comando que permite fazer requests à API.
7. Control Pane: expõe a API para gerenciamento dos clusters.
> The control plane's components make global decisions about the cluster (for example, scheduling), as well as detecting and responding to cluster events (for example, starting up a new pod when a deployment's `replicas` field is unsatisfied).

(https://d33wubrfki0l68.cloudfront.net/2475489eaf20163ec0f54ddc1d92aa8d4c87c96b/e7c81/images/docs/components-of-kubernetes.svg)

### Pods: os menores objetos em k8s

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

### Service: expondo aplicações na rede

> A way to expose an application running on a set of pods as a network service

1. ClusterIP: permite a comunicação entre pods **de dentro do Cluster**
Esse service está atrelado a um Pod. Assim, os service será responsável por direcionar todas as requisições feitas a ele para o Pod atralado. Para indicar qual Pod ele está atrelado, usamos **labels**.

- Definindo um service ClusterIP:

```yml
apiVersion: v1
kind: Service
metadata:
	name: svc-pod2
spec:
	type: ClusterIP
	selector:
		app: segundo-pod
	ports:
		- port: 9000
		  targetPort: 80
```
A label *app: segundo-pod* é em formato chave-valor e está definida no pod associado ao service:
```yml
apiVersion: v1
kind: Pod
metadata:
	name: pod2
	labels:
		app: segundo-pod
spec:
	containers:
		- name: pod2-container
		  image: nginx:latest
		  ports:
			- containerPort: 80
```

