Files
	*.wav
		devem ser colocados na pasta "/etc/verbose/"
		-> local definido em psmouse-base.c, nas fun��es verboseRight, verboseLeft e verboseMiddle
	psmouse.ko
		m�dulo compilado para a vers�o 3.13.0-30-generic do kernel
		Pode funcionar em outras vers�es
	psmouse-base.c
		arquivo fonte utilizado para gerar o psmouse.ko
		para compil�-lo, deve ser substitu�do na pasta "'kernel'/drivers/input/mouse"
			no meu caso "/usr/src/linux-3.13.0/drivers/input/mouse"
		nas fun��es 'verbose' deve-se alterar as configura��es da chamada aplay de acordo com a vers�o instalada