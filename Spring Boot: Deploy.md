---


---

<h1 id="deploy--docker">Deploy &amp; Docker</h1>
<h2 id="variáveis-de-ambiente">Variáveis de ambiente</h2>
<ul>
<li>
<p>No arquivo <code>application-prod.yaml</code>:</p>
<pre class=" language-yaml"><code class="prism  language-yaml"><span class="token key atrule">spring</span><span class="token punctuation">:</span>  
	 <span class="token key atrule">datasource</span><span class="token punctuation">:</span> 
		  <span class="token key atrule">driverClassName</span><span class="token punctuation">:</span> org.h2.Driver  
		  <span class="token key atrule">url</span><span class="token punctuation">:</span> $<span class="token punctuation">{</span>FORUM_DATABASE_URL<span class="token punctuation">}</span>  
		  <span class="token key atrule">username</span><span class="token punctuation">:</span> $<span class="token punctuation">{</span>FORUM_DATABASE_USERNAME<span class="token punctuation">}</span>  
		  <span class="token key atrule">password</span><span class="token punctuation">:</span> $<span class="token punctuation">{</span>FORUM_DATABASE_PASSWORD<span class="token punctuation">}</span>

<span class="token key atrule">forum</span><span class="token punctuation">:</span>  
	 <span class="token key atrule">jwt</span><span class="token punctuation">:</span> 
	      <span class="token key atrule">secret</span><span class="token punctuation">:</span> $<span class="token punctuation">{</span>FORUM_JWT_SECRET<span class="token punctuation">}</span>  
		  <span class="token key atrule">expiration</span><span class="token punctuation">:</span> <span class="token number">86400000</span>
</code></pre>
</li>
<li>
<p>Na linha de comando:</p>
<ol>
<li>PowerShell (lixo):</li>
</ol>
<pre class=" language-bash"><code class="prism  language-bash">java -jar <span class="token string">"-DFORUM_DATABASE_URL=jdbc:h2:mem:alura-forum"</span>
		  <span class="token string">"D-FORUM_DATABASE_USERNAME=sa"</span>
		  <span class="token string">"D-FORUM_DATABASE_PASSWORD="</span>
		  <span class="token string">"D-FORUM_JWT_SECRET=secret"</span>
		  <span class="token string">"-Dspring.profiles.active=prod"</span> <span class="token operator">&lt;</span>NomeDoJar<span class="token operator">&gt;</span>
</code></pre>
<ol start="2">
<li>Terminal decente:</li>
</ol>
<pre class=" language-bash"><code class="prism  language-bash"><span class="token function">export</span> FORUM_DATABASE_URL<span class="token operator">=</span>DATABASE_URL
<span class="token function">export</span> FORUM_DATABASE_USERNAME<span class="token operator">=</span>sa
<span class="token function">export</span> FORUM_DATABASE_PASSWORD<span class="token operator">=</span>sa
<span class="token function">export</span> FORUM_JWT_SECRET<span class="token operator">=</span>secret
java -jar -Dspring.profiles.active<span class="token operator">=</span>prod <span class="token operator">&lt;</span>NomeDoJar<span class="token operator">&gt;</span>
</code></pre>
</li>
</ul>
<h2 id="docker">Docker</h2>
<ul>
<li>
<p>Criar arquivo <code>Dockerfile</code>:</p>
<pre class=" language-dockerfile"><code class="prism  language-dockerfile"><span class="token keyword">FROM</span> openjdk<span class="token punctuation">:</span>11.0.10<span class="token punctuation">-</span>jre<span class="token punctuation">-</span>slim  
<span class="token keyword">ARG</span> JAR_FILE=target/*.jar  
<span class="token keyword">COPY</span> $<span class="token punctuation">{</span>JAR_FILE<span class="token punctuation">}</span> app.jar  
<span class="token keyword">ENTRYPOINT</span> <span class="token punctuation">[</span><span class="token string">"java"</span><span class="token punctuation">,</span> <span class="token string">"-jar"</span><span class="token punctuation">,</span> <span class="token string">"/app.jar"</span><span class="token punctuation">]</span>
</code></pre>
</li>
<li>
<p>Criando imagem:</p>
<pre class=" language-bash"><code class="prism  language-bash"><span class="token function">sudo</span> docker build -t alura/forum <span class="token keyword">.</span>
</code></pre>
</li>
<li>
<p>Executando imagem:</p>
<pre class=" language-bash"><code class="prism  language-bash"><span class="token function">sudo</span> docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE<span class="token operator">=</span><span class="token string">'prod'</span>
							 -e FORUM_DATABASE_URL<span class="token operator">=</span><span class="token string">'jdbc:h2:mem:alura-forum'</span>
							 -e FORUM_DATABASE_USERNAME<span class="token operator">=</span><span class="token string">'sa'</span>
							 -e FORUM_DATABASE_PASSWORD<span class="token operator">=</span><span class="token string">''</span>
							 -e FORUM_JWT_SECRET<span class="token operator">=</span><span class="token string">'secret'</span> alura/forum
</code></pre>
</li>
</ul>

