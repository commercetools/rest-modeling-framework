<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Exception\InvalidArgumentException;
use Test\Base\JsonObjectModel;

class AnimalModel extends JsonObjectModel implements Animal {
    const DISCRIMINATOR_VALUE = '';

    /**
     * @param array $data
     */
    public function __construct(array $data = []) {
        parent::__construct($data);
        $this->setKind(static::DISCRIMINATOR_VALUE);
    }

    /**
     * @var string
     */
    protected $kind;

    /**
     * @return string
     */
    public function getKind()
    {
        if (is_null($this->kind)) {
            $value = $this->raw(Animal::FIELD_KIND);
            $value = (string)$value;
            $this->kind = $value;
        }
        return $this->kind;
    }

    /**
     * @param string $kind
     * @return $this
     */
    public function setKind(string $kind)
    {
        $this->kind = (string)$kind;

        return $this;
    }

}
