Files
	*.wav
		devem ser colocados na pasta "/etc/verbose/"
		-> local definido em psmouse-base.c, nas funções verboseRight, verboseLeft e verboseMiddle
	psmouse.ko
		módulo compilado para a versão 3.13.0-30-generic do kernel
		Pode funcionar em outras versões
	psmouse-base.c
		arquivo fonte utilizado para gerar o psmouse.ko
		para compilá-lo, deve ser substituído na pasta "'kernel'/drivers/input/mouse"
			no meu caso "/usr/src/linux-3.13.0/drivers/input/mouse"
		nas funções 'verbose' deve-se alterar as configurações da chamada aplay de acordo com a versão instalada