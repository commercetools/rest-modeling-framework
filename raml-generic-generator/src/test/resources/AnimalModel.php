<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class AnimalModel extends JsonObject implements Animal {
    const DISCRIMINATOR_VALUE = '';

    public function __construct(array $data = []) {
        parent::__construct($data);
        $this->kind = static::DISCRIMINATOR_VALUE;
    }

    /**
     * @var string
     */
    private $kind;

    /**
     * @return string
     */
    public function getKind()
    {
        if (is_null($this->kind)) {
            $value = $this->raw('kind');
            $this->kind = (string)$value;
        }
        return $this->kind;
    }

    /**
     * @param string $kind
     * @return $this
     */
    public function setKind($kind)
    {
        $this->kind = (string)$kind;

        return $this;
    }

}
